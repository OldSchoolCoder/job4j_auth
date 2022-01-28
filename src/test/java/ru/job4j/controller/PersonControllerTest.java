package ru.job4j.controller;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.job4j.Job4jAuthApplication;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
class PersonControllerTest {

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private MockMvc mockMvc;

    private final Person person = new Person("parsentev", "123");

    @Test
    void findAll() throws Exception {
        List<Person> personList = Arrays.asList(person);
        when(personRepository.findAll()).thenReturn(personList);
        mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].login", contains("parsentev")));
    }

    @Test
    void findById() throws Exception {
        when(personRepository.findById(anyInt()))
                .thenReturn(Optional.of(person));
        mockMvc.perform(get("/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login",
                        equalTo("parsentev")));
    }

    @Test
    void create() throws Exception {
        when(personRepository.save(any())).thenReturn(person);
        ObjectMapper mapper = new ObjectMapper();
        String personAsString = mapper.writeValueAsString(person);
        mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personAsString))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.login",
                        equalTo("parsentev")));
        ArgumentCaptor<Person> personCaptor = ArgumentCaptor
                .forClass(Person.class);
        verify(personRepository).save(personCaptor.capture());
        assertThat(personCaptor.getValue().getLogin(),
                is("parsentev"));
    }

    @Test
    void update() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String personAsString = mapper.writeValueAsString(person);
        mockMvc.perform(put("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personAsString)
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
        ArgumentCaptor<Person> personCaptor = ArgumentCaptor
                .forClass(Person.class);
        verify(personRepository).save(personCaptor.capture());
        assertThat(personCaptor.getValue().getLogin(),
                is("parsentev"));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/person/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
        ArgumentCaptor<Person> personCaptor = ArgumentCaptor
                .forClass(Person.class);
        verify(personRepository).delete(personCaptor.capture());
        assertThat(personCaptor.getValue().getId(),
                is(1));
    }
}