package com.example.registration.controllers;

import com.example.registration.dto.RoomDto;
import com.example.registration.model.Room;
import com.example.registration.model.User;
import com.example.registration.service.RoomService;
import com.example.registration.service.UserService;
import com.example.registration.service.impl.RoomServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.registration.model.Role.USER;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @InjectMocks
    private AdminController adminController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private RoomService roomService;

    @Before("")
    void setUp() {
        roomService = Mockito.mock(RoomServiceImpl.class);
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

     User user1 = new User(1L, "Pipirka", "0666666", "test@test.com", "password", USER, null);
     User user2 = new User(2L, "Popka Durak", "07777777", "test2@test.com", "password2", USER, null);
     User user3 = new User(3L, "Padlo", "088888888", "test3@test.com", "password3", USER, null);

     Room room1 = new Room(1L, 1, BigDecimal.valueOf(100), "Good room");
     Room room2 = new Room(2L, 2, BigDecimal.valueOf(50), "Bad room");


    @Test
    void getUserById() {

        Mockito.when(userService.findById(1L)).thenReturn(Optional.of(user1));

        Optional<User> user = userService.findById(1L);
        assertEquals("Pipirka", user.get().getUsername());
        assertEquals("0666666", user.get().getPhone());
        assertEquals("test@test.com", user.get().getEmail());
        assertEquals("password", user.get().getPassword());
        assertEquals(USER, user.get().getRole());
    }

    @Test
    void getUserByIdNullTest() {
        User user = new User();
        when(userService.findById(1L)).thenReturn(Optional.of(user));
    }

    @Test
    void shouldReturn404WhenFindUserById() throws Exception{
        Mockito.when(userService.findById(user1.getId())).thenReturn(Optional.of(user1));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[1].username", is("Pipirka")));
   }

    @Test
    void getAllUsers() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(user1, user2, user3));

        Mockito.when(userService.getAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/allUsers")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].username", is("Padlo")));
    }

    @Test
    void deleteUser() {
        Mockito.when(userService.findById(1L)).thenReturn(Optional.of(user1)).thenReturn(null);

        verify(userService, times(1)).delete(1L);

        //Mockito.verify(userService, Mockito.times(1))
         //       .delete(Mockito.any());
    }

    @Test
    public void createRoom() throws Exception {
        Room room = Room.builder()
                .id(3L)
                .number(3)
                .price(BigDecimal.valueOf(500))
                .title("LUX")
                .build();

        //Mockito.when(roomService.save(room)).thenReturn(room);

        String content = objectWriter.writeValueAsString(room);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/addRoom")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is("LUX")));


//        Room room = new Room(1L, 1, BigDecimal.valueOf(100), "Good room");
//        roomService.save(room);

       // verify(roomService, times(1)).save(room);
    }

    @Test
    void addRoomFailed() {
        //Room room = new Room(1L, 1, BigDecimal.valueOf(100), "Good room");

//        room1.setId(1L);
//        Mockito.doReturn(new Room())
//                .when(roomService)
//                .findById(1L);
//
//        boolean isRoomCreated = roomService.save(room1);
//        Assertions.assertFalse(isRoomCreated);
    }

    @Test
    void updateRoom() {
        Room room = new Room(1L, 1, BigDecimal.valueOf(100), "Good room");
        RoomDto roomDto = mock(RoomDto.class);

    }

    @Test
    void deleteRoom() {
        Room room = new Room(1L, 1, BigDecimal.valueOf(100), "Good room");
        Mockito.when(roomService.findById(1L)).thenReturn(room);

        Mockito.verify(roomService, times(1))
                .deleteRoomId(1L);
    }
}