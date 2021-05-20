package com.backend.controller;

import com.backend.classes.Account;
import com.backend.classes.Transferencia;
import com.backend.classes.User;
import com.backend.interfaces.AccountRepository;
import com.backend.interfaces.TransferenciaRepository;
import com.backend.interfaces.UserAccountRepository;
import com.backend.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST}) //CORS
public class Controller {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    //Autowired busca un objeto manejado (beans) que implementen determinada interfaz para hacer referencia a él.
    //De esta manera no es necesario crear una instancia nueva del objeto cada vez que se necesite la funcionalidad de determinada clase.

    @RequestMapping("/clients")
    public Map<String, Object> getClients(Authentication authentication) {
        Map<String, Object> DTO = new LinkedHashMap<String, Object>();
        if (authentication == null) {
            DTO.put("user", null);
        }else{
            User user = userRepository.findByDni((authentication.getName()));
            DTO.put("user", user.UserDTO());
            DTO.put("account", accountRepository.findAll());
        }
        return DTO;
    }

    public Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
    /*

        @PostMapping("/user")
        public User addUser(@RequestBody String newUser){
            User addUser = userRepository.save(new User(newUser));
            return Application.addUser(addUser);
        }
    */
    //Métodos inutilizados
    /*
    @PostMapping("/user")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody User newUser){
        if (newUser.getDni().length() < 7 || newUser.getDni().length() > 8){
            System.out.println("El dni es incorrecto, por favor verifique!" +""+ HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(makeMap("Error", "DNI incorrecto"), HttpStatus.BAD_REQUEST);
        }
        if (newUser.getPassword().length() < 3 || newUser.getPassword().length() > 8){
            System.out.println("La contraseña debe tener mas de 3 digitos y menos de 8" +""+ HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(makeMap("Error", "Contraseña incorrecta"), HttpStatus.BAD_REQUEST);
        }

        if(userRepository.findByDni(newUser.getDni()) != null){
            System.out.println("ERROR, El usuario con el dni ingresado ya existe");
            return new ResponseEntity<>(makeMap("error", "\"El usuario con el dni ingresado ya existe\""), HttpStatus.FORBIDDEN);
        }
        User user1 = new User(newUser.getDni(), newUser.getPassword());
        user1 = userRepository.save(user1);
        System.out.println("Usuario Creado");
        return new ResponseEntity<>(makeMap("Succes", "User created"), HttpStatus.CREATED);
    }
    */

    //Método para crear una nueva Account
    @PostMapping("/account/{userDni}")
    @CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
    public Account addAccount(@RequestBody Account account, @PathVariable String userDni) {
        User user = userRepository.findByDni(userDni);
        Account newAccount = new Account(account.getTipoCuenta(), account.getSaldo(), user);
        newAccount = accountRepository.save(newAccount);
        return newAccount;
    }

    @RequestMapping("/account")
    public List<Account> allAccounts (){
        return accountRepository.findAll();
    }

    //Este método recibe la consulta del front y devuelve los datos de la cuenta
    @RequestMapping("/account/{userDni}/{tipoDni}")
    @CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity<Map<String, Object>> getAccount (@PathVariable String userDni, @PathVariable String tipoDni){
        if (userDni.length() < 6){
            System.out.println("El dni es incorrecto, por favor verifique!" +""+ HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(makeMap("Error", "DNI incorrecto"), HttpStatus.BAD_REQUEST);
        }
        if (userDni.length() > 8){
            System.out.println("El dni es incorrecto, por favor verifique!" +""+ HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(makeMap("Error", "DNI incorrecto, "), HttpStatus.BAD_REQUEST);
        }
        if(userRepository.findByDni(userDni) == null){
            System.out.println("ERROR, La cuenta con el dni ingresado no existe");
            return new ResponseEntity<>(makeMap("error", "\"El usuario con el dni ingresado no tiene cuenta\""), HttpStatus.FORBIDDEN);
        }
        User user = userRepository.findByDni(userDni);
        if (!tipoDni.equals(user.getTipoDni())){
            System.out.println("ERROR, El dni ingresado y el tipo de DNI no coincide");
            System.out.println(tipoDni + "//" + user.getTipoDni());
            return new ResponseEntity<>(makeMap("error", "\"El dni ingresado y el tipo de DNI no coincide\""), HttpStatus.FORBIDDEN);
        }
        if (tipoDni.equals(" ")){
            System.out.println("ERROR, El dni ingresado y el tipo de DNI no coincide");
            System.out.println(tipoDni + "//" + user.getTipoDni());
            return new ResponseEntity<>(makeMap("error", "\"El dni ingresado y el tipo de DNI no coincide\""), HttpStatus.FORBIDDEN);
        }
        Account account = accountRepository.findByUser(user);
        System.out.println("Localizacion exitosa:" + account.ActoDTO());
        return new ResponseEntity<>(account.ActoDTO(), HttpStatus.ACCEPTED);
    }

    //Método que recibe XML y crea una nueva transferencia
    @PostMapping(path= "/transferencia", consumes = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.ALL_VALUE})
    @CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity newTransferencia(@RequestBody Transferencia transferencia) {
        Transferencia newTransferencia = new Transferencia(transferencia.getFecha(), transferencia.getNombreOrigen(), transferencia.getCuentaOrigen(), transferencia.getNombreDestinatario(), transferencia.getCuentaDestino(), transferencia.getCbuDestinatario(), transferencia.getMoneda(), transferencia.getImporte(), transferencia.getPlazoAcreditacion(), transferencia.getConcepto(), transferencia.getNroComprobante());
        newTransferencia = transferenciaRepository.save(newTransferencia);
        System.out.println(transferencia);
        System.out.println(newTransferencia);
        return new ResponseEntity(makeMap("comprobante", newTransferencia.getId()), HttpStatus.ACCEPTED);
    }

    @RequestMapping("/user")
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @RequestMapping("/transferencia")
    public List<Transferencia> getTransferencias(){ return transferenciaRepository.findAll();
    }
}
