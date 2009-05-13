/*
 * Server.java
 *
 * Created on 17 gennaio 2007, 13.12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gestionecassa.server;

import gestionecassa.Amministratore;
import gestionecassa.Persona;
import gestionecassa.Cassiere;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import gestionecassa.Log;
import gestionecassa.exceptions.ActorAlreadyExistingException;
import gestionecassa.exceptions.WrongLoginException;

/** This is the main class of the server side application.
 *
 * @author ben
 */
public class Server extends UnicastRemoteObject implements ServerRMICommon {
    
    /**
     * reference to the main local business logic (serverBL)
     */
    public static Server businessLogicLocale;
    
    /**
     * List of the opened sessions
     */
    static List<SessionRecord> sessionList;

    /**
     * Semaphore for the list of opened sessions
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final String sessionListSemaphore = 
            new String("Semaphore" + System.currentTimeMillis());
    
    /**
     * reference to the timer and sessions manager.
     */
    ServerTimer ilTimer;
    
    /**
     * reference to the dataManager
     */
    DataManager dataManager;
    
    /**
     * Method that grants the creation of a singleton.
     */
    public synchronized static Server avvia() {
        if (businessLogicLocale == null) {
            try {
                businessLogicLocale = new Server();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
        return businessLogicLocale;
    }
    
    /**
     * Creates a new instance of Server
     */
    private Server() throws  RemoteException{
        sessionList = new ArrayList<SessionRecord> ();
        ilTimer = new ServerTimer();
        ilTimer.start();
        
        dataManager = new DataManager();
        
        Log.GESTIONECASSA_SERVER.info("Server started");
    }
    
    /**
     * The stopping Method
     */
    public void stopServer() {
        ilTimer.stopServer();
    }
    
    /**
     * The main that initializes and starts the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        java.util.Calendar tempCal = java.util.Calendar.getInstance();
//        tempCal.setTime(new java.util.Date());
//        String stringaData = String.format("%1$tY-%1$tm-%1te",tempCal);
//        System.out.println(stringaData);
        Server.avvia();
        
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        } catch (Exception e) {
            Log.GESTIONECASSA_SERVER.warn("Impossibile avviare un nuovo"+
                    " registry (e' forse gia' in esecuzione?)",e);
        }
        try {
            Naming.rebind("ServerRMI",businessLogicLocale);
        } catch (MalformedURLException e) {
            Log.GESTIONECASSA_SERVER.error("l'indirizzo e' sbagliato: ",e);
        } catch (RemoteException e) {
            Log.GESTIONECASSA_SERVER.error("Impossibile accedere al registry: ",e);
        }

        // ora avvia gli altri servizi
    }
    
    /**
     * returns the id of the user in the table
     *
     * @return the id of the user in the table
     */
    public int getIdTabella(int sessionID)throws  RemoteException{
        return (sessionList.get(sessionID).idTabella);
    }
    
    /**
     * Method which both the clients use to log themselves in.
     *
     * @param   username    username through which they can access
     * to their own data.
     * @param   password    the password used by the user.
     *
     * @throws RemoteException Throws a remote exception, because we are aon RMI context.
     * @throws WrongLoginException
     *
     * @return  The id of the user, which is used in comunication, once logged.
     */
    public int sendRMILoginData(String username, String password) 
            throws    RemoteException, WrongLoginException{
        return logga(username,password);
    }

    /**
     * Method which both the clients use to register themselves in.
     *
     * @param   user    The user who want's to be registered.
     *
     * @throws RemoteException Throws a remote exception, because we are aon RMI context.
     * @throws ActorAlreadyExistingException
     * @throws WrongLoginException
     *
     * @return  The id of the user, which is used in comunication, once logged.
     */
    public int sendRMIDatiRegistrazione(Persona user)
            throws    RemoteException, ActorAlreadyExistingException, WrongLoginException{

        String username = user.getUsername();
        String password = user.getPassword();

        //se lo username non e' presente lo posso registrare.
        if (verificaUsername(username,password) == null) {
            registra(user);
            return logga(username,password);
        } else {
            throw new ActorAlreadyExistingException();
        }
    }
    
    /**
     * This Method registers a new Actor.
     *
     * @param   user    data of the new Cassiere.
     */
    private void registra(final Persona user) {
        int id = dataManager.registraUtente(user);
        //dell'id nella tabella ce ne facciamo poco in questo punto.
        Log.GESTIONECASSA_SERVER.info("Registrato nuovo " + user.getClass()
                + " con id (nella sua tabella): " + id);
    }
    
    /** Logs a user, through his username and password.
     *
     * @param   username    user's username.
     * @param   password    user's password
     *
     * @return  the session id.
     */
    private int logga(final String username, final String password)
            throws    WrongLoginException, RemoteException{
        SessionRecord tempRecord = new SessionRecord();
        /* Controlla che i dati dell'utente siano presenti nel
         * database degli utenti registrati*/
        
        tempRecord.user = verificaUsername(username,password);
        //anche se mi son appena registrato, e' importante: fa un doppio check.
        
        if (tempRecord.user == null) {
            //questo numero indica che l'utente non e' stato trovato nel db
            throw new WrongLoginException();
        } //se non esiste restituisco un errore.
        
        /* Prima constrolla che le password coincidano, poi guarda nella lista
         * degli utenti collegati e determina un nuovo session id
         */
        ServiceThreadCommon srv = null;
        try {
            if (tempRecord.user instanceof Cassiere) {
                
                // se la password non e' giusta lo dico al client
                if (!((Cassiere)tempRecord.user).getPassword().equals(password))
                    throw new WrongLoginException();
                
                tempRecord.idTabella = ((Cassiere)tempRecord.user).getId();
                srv = new ServerRMICassiereImpl(tempRecord,dataManager);
            } else if(tempRecord.user instanceof Amministratore){
                
                // se la password non e' giusta lo dico al client
                if (!((Amministratore)tempRecord.user).getPassword().equals(password))
                    throw new WrongLoginException();
                
                tempRecord.idTabella = ((Amministratore)tempRecord.user).getId();
                srv = new ServerRMIAmministratoreImpl(tempRecord,dataManager);
            }
        } catch (RemoteException ex) {
            Log.GESTIONECASSA_SERVER.error("Errore nell'instanziazione dell'" +
                    "oggetto del working thread",ex);
            throw ex;
        }
        
        tempRecord.username = username;
        tempRecord.relatedThread = srv;
        tempRecord.clientId = nuovaSessione(tempRecord);
        
        try {
            Naming.rebind("Server"+tempRecord.clientId,srv);
        } catch (MalformedURLException ex) {
            Log.GESTIONECASSA_SERVER.error("L'indirzzo verso cui fare il" +
                    " bind del working thread e' sbagliato",ex);
        } catch (RemoteException ex) {
            Log.GESTIONECASSA_SERVER.error("non e' stato possible registrare" +
                    " la classe del working thread: remote exception",ex);
        }
        srv.start();
        
        return tempRecord.clientId;
    }
    
    /** This method verifies that the username is free.
     *
     * @param username  the username to verify.
     *
     * @return Reference to the user found. null if nobody found.
     */
    private final Persona verificaUsername(final String username, final String password) {
        
        /* Controlla prima in una tabella poi se non ottieni risultato,
         nella'tlra e restituisci l'oggetto corrispondente alla perosna
         che sta cercando di loggarsi. Se non trovi nessun restituisci null*/

        Persona user = dataManager.verificaUsername(username,password);
        
        if (user != null) {
            if (user instanceof Amministratore) {
                Log.GESTIONECASSA_SERVER.debug("Trovato utente fra gli Amministratori");
            } else {
                Log.GESTIONECASSA_SERVER.debug("Trovato utente fra i Cassieri");
            }
        }
        
        return user;
        
    }
    
    /** This method looks for the first free number in sessions list.
     *
     * @param newRecord  the record to verify.
     *
     * @return new sessionId.
     */
    final int nuovaSessione(SessionRecord newRecord) {
        int id = 0;
        synchronized (sessionListSemaphore) {
            id = Server.sessionList.indexOf(newRecord);
            /* se non lo trova, mi restituisce -1 */
            if (id == -1) {
                /*vedo se esistono posti intermedi liberi.
                  infatti se un thread implode lascia uno spazio libero.*/
                int count = 0;
                for (SessionRecord elem : sessionList) {
                    if (elem.clientId == -1) {
                        break;
                    }
                    count++;
                }
                id = count;

                /*gli do l'ultimo posto se non trovo posti in mezzo*/
                if (id == sessionList.size()) {
                    sessionList.add(newRecord);
                } else {
                    sessionList.set(id,newRecord);
                }
            } else {
                /*se lo trova vuol dire che si sta riconnettendo..
                 gli assegno lo stesso id*/
                Server.sessionList.set(id,newRecord);
            }
        }
        return id;
    }
    
    /** This method destoryes a record in the sessions' list.
     *
     * @param   session     the session to destroy.
     */
    final void eraseSession(SessionRecord session) {
        synchronized (sessionListSemaphore) {
            session.clientId = -1;
            session.user = null;
            session.username = new String("");
            session.relatedThread.stopThread();
        }
        Log.GESTIONECASSA_SERVER.debug("Eliminata la sessioen scaduta o" +
                " terminata");
    }
    
    /** Method that tell's the server that the client still
     * lives and is connected.
     *
     * @throws  RemoteException because we are in RMI context.
     */
    public void keepAlive(int sessionID) throws  RemoteException{
        synchronized (sessionListSemaphore) {
            sessionList.get(sessionID).timeElapsed = 0;
        }
    }
    
    /** Method that tell's to the thread to shut down.
     *
     * @throws  RemoteException because we are in RMI context.
     */
    public void closeService(int sessionID) throws  RemoteException{
        /*timer will do the rest.*/
        synchronized (sessionListSemaphore) {
            eraseSession(sessionList.get(sessionID));
        }
    }
}
