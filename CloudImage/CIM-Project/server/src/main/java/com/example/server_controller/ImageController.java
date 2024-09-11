package com.example.server_controller; 

import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.jsonwebtoken.io.IOException;

@RestController
public class ImageController {
  // private static final Logger logger = LoggerFactory.getLogger(userController.class);

  private final LinkRepository linkRepository; 
  private final s3service s3; 
  public ImageController(LinkRepository linkRepository, s3service s3) {
    this.linkRepository = linkRepository; 
    this.s3 = s3; 
  } 

  @PostMapping("/user/{userid}/images") 
  public ResponseEntity<Response> addImage(@RequestParam("file") MultipartFile imgToUpload, @RequestHeader("Authorization") String auth, @PathVariable("userid") String userId) 
                                          throws IOException, java.io.IOException { 
    String username = jwtUtils.getUsername(auth); 
    if (!username.equals(userId)) { 
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); 
    }
    if(jwtUtils.rwPermissions(auth)){ 
      String savedAs = this.s3.uploadFile(username, imgToUpload.getOriginalFilename(),imgToUpload); 
      this.linkRepository.save(new Link(username, savedAs)); 
      return ResponseEntity.status(HttpStatus.OK).body(new Response("File Creation Success", null)); 
    } else { 
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response("File Creation Failed - No Permission", null)); 
    }
  } 
  @GetMapping("/user/images") 
  public ResponseEntity<Iterable<Link>> getAllImage() { 
    return ResponseEntity.ok(this.linkRepository.findAll()); 
  } 

  @GetMapping("user/{userid}/images") 
  public ResponseEntity<String[]> getUserImages(@RequestHeader("Authorization") String auth, @PathVariable("userid") String userId) { 
    String username = jwtUtils.getUsername(auth); 
    if (!username.equals(userId)) { 
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); 
    }
    String[] images = this.linkRepository.getUserImages(username); 
    return ResponseEntity.ok(this.s3.getUsersURLs(images)); 
  } 
} 