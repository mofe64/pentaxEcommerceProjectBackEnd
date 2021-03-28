package com.pentax.pentazon.services;

import com.pentax.pentazon.dtos.*;
import com.pentax.pentazon.exceptions.*;
import com.pentax.pentazon.models.*;
import com.pentax.pentazon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadAUserByUsername(username);
    }

    private UserDetails loadAUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    @Override
    public UserDTO registerBuyer(UserDTO buyerDTO) throws UserException {
        User buyer = UserDTO.unpackDTO(buyerDTO);
        if(emailExists(buyer.getEmail())){
            throw new UserException("Email already exists");
        }
        if(usernameExists(buyer.getUsername())){
            throw new UserException("Username already exists");
        }

        Role buyerRole = null;
        try {
            buyerRole = roleService.findByName("BUYER");
        } catch (UserRoleNotFoundException userRoleNotFoundException) {
            userRoleNotFoundException.printStackTrace();
        }
        if (buyerRole != null) {
            buyer.getRoles().add(buyerRole);
        }
        buyer.setPassword(bCryptPasswordEncoder.encode(buyer.getPassword()));
        Cart cart = cartService.createCart();
        buyer.setCartId(cart.getId());
        User savedBuyer = registerABuyer(buyer);
        return UserDTO.packDTO(savedBuyer);
    }

    private User registerABuyer(User buyer) {
        return userRepository.save(buyer);
    }

    @Override
    public UserDTO registerSeller(UserDTO sellerDTO) throws UserException {
        User seller = UserDTO.unpackDTO(sellerDTO);
        if(emailExists(seller.getEmail())){
            throw new UserException("Email already exists");
        }
        if(usernameExists(seller.getUsername())){
            throw new UserException("Username already exists");

        }
        Role sellerRole = null;
        Role buyerRole = null;
        try {
            sellerRole = roleService.findByName("SELLER");
            buyerRole = roleService.findByName("BUYER");
        } catch (UserRoleNotFoundException userRoleNotFoundException) {
            userRoleNotFoundException.printStackTrace();
        }
        if (sellerRole != null) {
            seller.getRoles().add(sellerRole);
            seller.getRoles().add(buyerRole);
        }
        seller.setPassword(bCryptPasswordEncoder.encode(seller.getPassword()));
        Cart cart = cartService.createCart();
        seller.setCartId(cart.getId());
        User savedSeller = registerASeller(seller);
        return UserDTO.packDTO(savedSeller);
    }

    @Override
    public CartDTO getUserCart(String userId) throws UserException, CartException {
        String cartId = findAUserById(userId).getCartId();
        System.out.println("cartId is " +cartId);
        CartDTO cartDTO = cartService.findCartById(cartId);
        System.out.println("userService");
        System.out.println(cartDTO);
        return cartDTO;
    }

    @Override
    public UserDTO findUserById(String userId) throws UserException {
        return UserDTO.packDTO(findAUserById(userId));
    }

    @Override
    public void addAddress(String userId, Address address) throws UserException {
        User user = findAUserById(userId);
        user.getAddresses().add(address);
        saveUser(user);
    }
    private void saveUser(User user){
        userRepository.save(user);
    }

    @Override
    public void addProductToCart(String productId, String userId, int quantity) throws UserException, ProductException, CartException {
        User user = findAUserById(userId);
        String cartId = user.getCartId();
        cartService.addItemToCart(productId,quantity, cartId);
    }

    @Override
    public void removeProductFromCart(String productId, String userId, int quantity) throws UserException, CartException {
        User user = findAUserById(userId);
        String cartId = user.getCartId();
        cartService.removeItemFromCart(cartId, productId);
    }

    @Override
    public List<Address> getUserAddresses(String userId) throws UserException {
        User user = findAUserById(userId);
        return user.getAddresses();
    }


    private User findAUserById(String userId) throws UserException {
       Optional<User> userOptional = userRepository.findUserById(userId);
       if(userOptional.isPresent()){
           return userOptional.get();
       } else {
           throw new UserException("No User Found with that Id");
       }
    }

    private User registerASeller(User seller) {
        return userRepository.save(seller);
    }

    private Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    private boolean emailExists(String email){
        return userRepository.findUserByEmail(email).isPresent();
    }
    private boolean usernameExists(String username){
        return userRepository.findUserByUsername(username).isPresent();
    }


}
