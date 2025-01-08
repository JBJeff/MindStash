package com.restApi.RestApi.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.restApi.RestApi.Basics.controller.*;
import com.restApi.RestApi.Basics.dto.*;
import com.restApi.RestApi.Basics.entity.*;
import com.restApi.RestApi.Basics.repository.UserRepository;
import com.restApi.RestApi.Basics.service.*;

public class ControllerUnitTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private UserService userService;

    @Mock
    private NoteService noteService;

    @Mock
    private MediaService mediaService;
    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private UserController userController;

    @InjectMocks
    private NoteController noteController;

    @InjectMocks
    private MediaController mediaController;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test NoteController - Add Note
    @Test
    void testAddNote_Success() {
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setUserId(1L);
        noteRequest.setCategoryId(2L);
        noteRequest.setTitle("Test Note");
        noteRequest.setContent(" das ist ein Test.");

        Note note = new Note();
        note.setId(1);
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());

        when(noteService.addNoteToCategory(anyLong(), anyLong(), anyString(), anyString())).thenReturn(note);

        ResponseEntity<Note> response = noteController.addNote(noteRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(noteRequest.getTitle(), response.getBody().getTitle());
    }

    @Test
    void testAddNote_UserOrCategoryNotFound() {
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setUserId(1L);
        noteRequest.setCategoryId(2L);
        noteRequest.setTitle("Test Note");
        noteRequest.setContent("das ist ein Test.");

        when(noteService.addNoteToCategory(anyLong(), anyLong(), anyString(), anyString()))
                .thenThrow(new IllegalArgumentException("Benutzer oder Kategorie nicht gefunden"));

        ResponseEntity<Note> response = noteController.addNote(noteRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    // Test UserController - Register User
    @Test
    void testRegisterUser_Success() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail("test@t-online.de");
        request.setPassword("password123");
        request.setFirstName("Jeff");
        request.setLastName("Boe");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword");

        ResponseEntity<?> response = userController.registerUser(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Benutzer erfolgreich registriert", response.getBody());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail("test@t-online.de");
        request.setPassword("password123");
        request.setFirstName("Jeff");
        request.setLastName("Boe");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        ResponseEntity<?> response = userController.registerUser(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("E-Mail bereits registriert", response.getBody());
        verify(userRepository, never()).save(any(User.class));
    }

    // Test UserController - Get All Users
    @Test
    void testGetAllUsers_Success() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@t-online.de");
        user.setFirstName("Jeff");
        user.setLastName("Boe");

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetAllUsers_NoContent() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    // Test CategoryController - Create Category
    @Test
    void testCreateCategory_Success() {
        Long userId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("TestCategory");

        when(categoryService.createCategory(userId, categoryDTO.getName()))
                .thenReturn(new com.restApi.RestApi.Basics.entity.Category());

        ResponseEntity<CategoryDTO> response = categoryController.createCategory(userId, categoryDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("TestCategory", response.getBody().getName());
    }

    // Test CategoryController - Get Categories for User
    @Test
    void testGetCategoriesForUser_Success() {
        Long userId = 1L;

        com.restApi.RestApi.Basics.entity.Category category = new com.restApi.RestApi.Basics.entity.Category();
        category.setId(1L);
        category.setName("TestCategory");

        when(categoryService.getCategoriesForUser(userId))
                .thenReturn(Collections.singletonList(category));

        ResponseEntity<List<CategoryDTO>> response = categoryController.getCategoriesForUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetCategoriesForUser_NoContent() {
        Long userId = 1L;

        when(categoryService.getCategoriesForUser(userId)).thenReturn(Collections.emptyList());

        ResponseEntity<List<CategoryDTO>> response = categoryController.getCategoriesForUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }


}
