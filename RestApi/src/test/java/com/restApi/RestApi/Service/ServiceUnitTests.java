package com.restApi.RestApi.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.restApi.RestApi.Basics.dto.CustomUserDetails;
import com.restApi.RestApi.Basics.entity.*;
import com.restApi.RestApi.Basics.repository.*;
import com.restApi.RestApi.Basics.service.*;

import java.time.LocalDateTime;
import java.util.*;

public class ServiceUnitTests {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private NoteService noteService;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Tests for NoteService

    @Test
    void testAddNoteToCategory_Success() {
        Long userId = 1L;
        Long categoryId = 1L;
        String title = "Test Note";
        String content = "Test.";

        User user = new User();
        user.setId(userId);

        Category category = new Category();
        category.setId(categoryId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Note note = new Note();
        note.setId(1);
        note.setTitle(title);
        note.setContent(content);
        note.setUser(user);
        note.setCategory(category);
        note.setCreatedAt(LocalDateTime.now());

        when(noteRepository.save(any(Note.class))).thenReturn(note);

        Note result = noteService.addNoteToCategory(userId, categoryId, title, content);

        assertNotNull(result);
        assertEquals(title, result.getTitle());
        assertEquals(content, result.getContent());
        assertEquals(user, result.getUser());
        assertEquals(category, result.getCategory());
    }

    @Test
    void testAddNoteToCategory_UserNotFound() {
        Long userId = 1L;
        Long categoryId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            noteService.addNoteToCategory(userId, categoryId, "Test Note", "Test.");
        });

        assertEquals("Benutzer nicht gefunden", exception.getMessage());
    }

    // Tests for CustomUserDetailsService

    @Test
    void testLoadUserByUsername_Success() {
        String email = "test@t-online.de";

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash("hashedPassword");
        user.setIsActive(true);
        user.setId(1L);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("hashedPassword", userDetails.getPassword());
        assertEquals(1L, userDetails.getUserId());
    }

    
    // Tests for CategoryService

    @Test
    void testCreateCategory_Success() {
        Long userId = 1L;
        String categoryName = "TestCategory";

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Category category = new Category();
        category.setId(1L);
        category.setName(categoryName);
        category.setUser(user);
        category.setCreatedAt(LocalDateTime.now());

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.createCategory(userId, categoryName);

        assertNotNull(result);
        assertEquals(categoryName, result.getName());
        assertEquals(user, result.getUser());
    }

    @Test
    void testCreateCategory_UserNotFound() {
        Long userId = 1L;
        String categoryName = "TestCategory";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.createCategory(userId, categoryName);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetCategoriesForUser_Success() {
        Long userId = 1L;

        Category category = new Category();
        category.setId(1L);
        category.setName("TestCategory");

        when(categoryRepository.findByUserId(userId)).thenReturn(Collections.singletonList(category));

        List<Category> result = categoryService.getCategoriesForUser(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("TestCategory", result.get(0).getName());
    }
}
