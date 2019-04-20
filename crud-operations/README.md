# CRUD Operations (Without Reactive)

1. Create new Spring Boot project
2. To make a CRUD operations program, we need:
   * Model
   * Controller
   * Service
3. First, create **User** Model as a usual POJO 
    ```java
    public class User {
        private Integer userid;
        private String username;

        public User(Integer userid, String username) {
            this.userid = userid;
            this.username = username;
        }

        // setter and getter
    }
    ```
4. Create the controller named **UserController** that includes GET, POST, PUT, and DELETE methods.
   
    ```java
    @RestController
    @RequestMapping("/user")

    public class UserController {
        @Autowired
        UserService userService;
        
        // GET method

        // Get all users
        @ResponseBody
        @RequestMapping("")
        public List<User> getAllUsers() {
            return userService.getAllUsers();
        }
        
        // Get specific user by id
        @ResponseBody
        @RequestMapping(value = "/{id}")
        public User getUser(@PathVariable("id") Integer id) {
            return userService.getUser(3);
        }
        
        // POST method
        @ResponseBody
        @RequestMapping(value = "", method = RequestMethod.POST)
        public Map<String, Object> createUser(
            @RequestParam(value = "userid") Integer userid,
            @RequestParam(value = "username") String username)
        {
            Map<String, Object> map = new LinkedHashMap<>();
            userService.createUser(userid, username);
            map.put("result", "added");
            return map;
        }
        
        // PUT method
        @ResponseBody
        @RequestMapping(value = "", method = RequestMethod.PUT)
        public Map<String, Object> updateUser(
                @RequestParam(value = "userid") Integer userid,
                @RequestParam(value = "username") String username)
        {
            Map<String, Object> map = new LinkedHashMap<>();
            userService.updateUser(userid, username);
            map.put("result", "updated");
            return map;
        }
        
        // DELETE method
        @ResponseBody
        @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
        public Map<String, Object> deleteUser(
                @PathVariable("id") Integer userid) 
        {
            Map<String, Object> map = new LinkedHashMap<>();
            userService.deleteUser(userid);
            map.put("result", "deleted");
            return map;
        }
    }
    ```
5. Create **UserService** Interface
    ```java
    public interface UserService {
        List<User> getAllUsers();
        User getUser(Integer userid);
        void createUser(Integer userid, String username);
        void updateUser(Integer userid, String username);
        void deleteUser(Integer userid);
    }
    ```
6. Then, create **UserServiceImpl** that implements UserService Interface
   * GET
        ```java
        // Get all users
        @Override
        public List<User> getAllUsers() {
            return this.users;
        }

        // Get specific user by id
        @Override
        public User getUser(Integer userid) {
            return users.stream().filter(x -> x.getUserId() == userid).findAny().orElse(new User(0, "Not available"));
        }
        ```
    * POST
        ```java
        @Override
        public void createUser(Integer userId, String username) {
            User user = new User(userId, username);
            this.users.add(user);
        }
        ```
    * PUT
        ```java
        @Override
        public void updateUser(Integer userid, String username) {
            users.stream().filter(x -> x.getUserId() == userid).findAny().orElseThrow(() -> new RuntimeException("Item not found")).setUsername(username);
        }
        ```
    * DELETE
        ```java
        @Override
        public void deleteUser(Integer userid) {
            users.removeIf((User u) -> u.getUserId() == userid);
        }
        ```
7. Add dummy data in the UserServiceImpl
    ```java
    // dummy users
	public static List<User> users;
	public UserServiceImpl() {
		users = new LinkedList<>();
		users.add(new User(1, "Hafara"));
		users.add(new User(2, "Akbar"));
		users.add(new User(3, "Alam"));
	}
    ```
8. Run the project by `Run As... > Spring Boot App`
9. Check each method using browser and Postman
    * GET
      * Get all users

        <img src="/img/ss5.png" width="500">

      * Get specific user by id

        <img src="/img/ss6.png" width="500">

    * POST (Create user)
        
        ![ss7](/img/ss7.png)

    * PUT (Update user)

        ![ss8](/img/ss8.png)

        Check whether the POST and PUT method succeed

        ![ss9](/img/ss9.png)

    * DELETE (Delete user by id)

        ![ss10](/img/ss10.png)

        Check whether the DELETE method succeed
       
        ![ss11](/img/ss11.png)


## Full Code

[Simple CRUD Operations Program](book-management/)