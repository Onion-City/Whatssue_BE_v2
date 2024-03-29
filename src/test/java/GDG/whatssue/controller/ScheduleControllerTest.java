package GDG.whatssue.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import GDG.whatssue.dto.schedule.AddScheduleRequestDto;
import GDG.whatssue.dto.schedule.GetScheduleResponseDto;
import GDG.whatssue.dto.schedule.ModifyScheduleRequestDto;
import GDG.whatssue.service.ScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class ScheduleControllerTest {

    @Mock
    ScheduleService scheduleService;

    @InjectMocks
    ScheduleController scheduleController;
    MockMvc mvc;
    ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders
            .standaloneSetup(scheduleController)
            .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("일정 추가 성공 컨트롤러 테스트")
    public void addScheduleSuccessTest() throws Exception {
        //given
        AddScheduleRequestDto dto = AddScheduleRequestDto.builder()
            .scheduleName("test")
            .scheduleContent("testSchedule")
            .scheduleDateTime(LocalDateTime.now())
            .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        //stub
        doNothing().when(scheduleService)
            .saveSchedule(anyLong(), any(AddScheduleRequestDto.class));

        //when, then
        mvc.perform(post("/api/1/schedules")
            .content(objectMapper.writeValueAsString(dto))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(scheduleService).saveSchedule(eq(1L), any(AddScheduleRequestDto.class));
    }
    
    @Test
    @DisplayName("일정 추가 실패 컨트롤러 테스트")
    void addScheduleFailTest() throws Exception{
        //given
        AddScheduleRequestDto dto = AddScheduleRequestDto.builder()
            .scheduleName("test")
            .scheduleContent("testSchedule")
            .scheduleDateTime(LocalDateTime.now())
            .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        //stub
        doThrow(NoSuchElementException.class).
            when(scheduleService).saveSchedule(anyLong(), any(AddScheduleRequestDto.class));

        //when, then
        mvc.perform(post("/api/1/schedules")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(scheduleService).saveSchedule(eq(1L), any(AddScheduleRequestDto.class));
    }

    @Test
    @DisplayName("일정 상세조회 성공 컨트롤러 테스트")
    void getScheduleSuccessTest() throws Exception {
        //given

        //stub
        when(scheduleService.findSchedule(eq(1L))).thenReturn(any(GetScheduleResponseDto.class));

        //when, then
        mvc.perform(get("/api/1/schedules/1"))
            .andExpect(status().isOk());

        verify(scheduleService).findSchedule(eq(1L));
    }

    @Test
    @DisplayName("일정 상세조회 실패 컨트롤러 테스트")
    void getScheduleFailTest() throws Exception {
        //given

        //stub
        doThrow(NoSuchElementException.class)
            .when(scheduleService).findSchedule(anyLong());

        //when, then
        mvc.perform(get("/api/1/schedules/1"))
            .andExpect(status().isNotFound());

        verify(scheduleService).findSchedule(eq(1L));
    }

    @Test
    @DisplayName("일정 수정 성공 컨트롤러 테스트")
    void modifyScheduleSuccessTest() throws Exception {
        //given
        ModifyScheduleRequestDto dto =
            ModifyScheduleRequestDto.builder()
                .scheduleName("modifyTest")
                .scheduleContent("test")
                .scheduleDateTime(LocalDateTime.now())
                .build();

        //stub
        doNothing().when(scheduleService).updateSchedule(anyLong(), any(ModifyScheduleRequestDto.class));

        //when, then
        mvc.perform(patch("/api/1/schedules/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk());

        verify(scheduleService).updateSchedule(eq(1L), any(ModifyScheduleRequestDto.class));
    }
    
    @Test
    @DisplayName("일정 수정 실패 컨트롤러 테스트")
    void modifyScheduleFailTest() throws Exception {
        //given
        ModifyScheduleRequestDto dto =
            ModifyScheduleRequestDto.builder()
                .scheduleName("modifyTest")
                .scheduleContent("test")
                .scheduleDateTime(LocalDateTime.now())
                .build();

        //stub
        doThrow(NoSuchElementException.class)
            .when(scheduleService).updateSchedule(anyLong(), any(ModifyScheduleRequestDto.class));

        //when, then
        mvc.perform(patch("/api/1/schedules/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isNotFound());

        verify(scheduleService).updateSchedule(eq(1L), any(ModifyScheduleRequestDto.class));
    }
    
    @Test
    @DisplayName("일정 삭제 성공 컨트롤러 테스트")
    void deleteScheduleSuccessTest() throws Exception {
        //given

        //stub
        doNothing().when(scheduleService).deleteSchedule(anyLong());

        //when, then
        mvc.perform(delete("/api/1/schedules/1"))
            .andExpect(status().isOk());

        verify(scheduleService).deleteSchedule(eq(1L));
    }

}