package ec.sevolutivo.sevuelos.sevuelos.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.sevolutivo.sevuelos.sevuelos.domain.Request;
import ec.sevolutivo.sevuelos.sevuelos.domain.enumeration.RequestStatus;
import ec.sevolutivo.sevuelos.sevuelos.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class RequestResourceTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RequestRepository requestRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Request request = new Request();
    private final Request completeRequest = new Request();
    private final Request completeRequest2 = new Request();

    @BeforeEach
    void setUp(){
        request.setPassenger("Johanan");
        request.setDestination("Quito");

        completeRequest.setId(1L);
        completeRequest.setDestination("Cuenca");
        completeRequest.setPassenger("Nicolas");
        completeRequest.setStatus(RequestStatus.NEW);


    }

    @Test
    public void shouldCreateARequest() throws Exception {
        int databaseSizeBeforeCreateRequest = requestRepository.findAll().size();

        mockMvc.perform(post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)) )
                        .andExpect(status().isOk());

        List<Request> listRequest = requestRepository.findAll();
        assertThat(listRequest).hasSize(databaseSizeBeforeCreateRequest+1);

        Request lastRequestFromListRequest = listRequest.get(listRequest.size()-1);
        assertEquals(request.getPassenger(), lastRequestFromListRequest.getPassenger());
        assertEquals(request.getDestination(), lastRequestFromListRequest.getDestination());
        assertEquals(RequestStatus.NEW,lastRequestFromListRequest.getStatus());
    }

    @Test
    public void shouldGetAllRequests() throws Exception {
        requestRepository.saveAndFlush(completeRequest);

        mockMvc.perform(get("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                    .andExpect(jsonPath("$[*].id").value(hasItem(completeRequest.getId().intValue())))
                    .andExpect(jsonPath("$[*].passenger").value(hasItem(completeRequest.getPassenger())))
                    .andExpect(jsonPath("$[*].destination").value(hasItem(completeRequest.getDestination())))
                    .andExpect(jsonPath("$[*].status").value(hasItem(completeRequest.getStatus().toString())));
    }

    @Test
    public void shouldGetRequestById() throws Exception {
        completeRequest2.setId(2L);
        completeRequest2.setDestination("Quito");
        completeRequest2.setPassenger("Johanan");
        completeRequest2.setStatus(RequestStatus.NEW);

        requestRepository.saveAndFlush(completeRequest);
        requestRepository.saveAndFlush(completeRequest2);

        mockMvc.perform(get("/api/requests/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(completeRequest2.getId().intValue()))
                        .andExpect(jsonPath("$.passenger").value(completeRequest2.getPassenger()))
                        .andExpect(jsonPath("$.destination").value(completeRequest2.getDestination()))
                        .andExpect(jsonPath("$.status").value(completeRequest2.getStatus().toString()));
    }

    @Test
    public void shouldChangeStatusToReserve() throws Exception {

        mockMvc.perform(put("/api/reserve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(completeRequest)))
                        .andExpect(status().isOk());
        Optional<Request> requestFromDataBaseAfterUpdate = requestRepository.findById(completeRequest.getId());
        assertEquals(RequestStatus.RESERVED, requestFromDataBaseAfterUpdate.get().getStatus() );
    }

}