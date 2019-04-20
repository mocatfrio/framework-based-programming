# File Uploads

1. Make a Spring Boot project
2. To make a CRUD operations program, we need:
   * Controller
   * Service
3. Create the controller named **FileController** that includes POST methods.
   
    ```java
    @RestController
    @RequestMapping("/file")

    public class FileController {
        @Autowired
        FileUploadService fileUploadService;
        
        @ResponseBody
        @RequestMapping(value = "/upload", method = RequestMethod.POST)
        public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file) {
            Map<String, Object> map = new LinkedHashMap<> ();
            try {
                fileUploadService.uploadFile(file);
                map.put("result", "file uploaded");
            } catch (IOException e) {
                map.put("result", "error while uploading : " + e.getMessage());
            }
            return map;
        }
    }
    ```
4. Create **FileUploadService** Interface
    ```java
    public interface FileUploadService {
        void uploadFile(MultipartFile file) throws IOException;
    }
    ```
5. Then, create **FileUploadServerImpl** that implements FileUploadService Interface
    ```java
    @Service
    public class FileUploadServerImpl implements FileUploadService {
        private Path location;
        public FileUploadServerImpl() throws IOException {
            location= Paths.get("/Users/mocatfrio/Downloads/");
            Files.createDirectories(location);
        }
        @Override
        public void uploadFile(MultipartFile file) throws IOException {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if(fileName.isEmpty()) {
                throw new IOException("File is empty " + fileName);
            } try {
                Files.copy(file.getInputStream(), this.location.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new IOException("File Upload Error : " + fileName);
            }
        }
    }
    ```
8. Run the project by `Run As... > Spring Boot App`
9. Check file uploads using Postman
       
    ![ss12](/img/ss12.png)

    The file is uploaded!

    ![ss13](/img/ss13.png)

## Full Code

[Simple File Upload Program](book-management/)