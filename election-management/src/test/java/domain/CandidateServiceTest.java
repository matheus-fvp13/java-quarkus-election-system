package domain;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.instancio.Instancio;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class CandidateServiceTest {

    @Inject
    CandidateService service;

    @InjectMock
    CandidateRepository repository;

    @Test
    void save() {
        var candidate = Instancio.create(Candidate.class);

        service.save(candidate);

        verify(repository).save(candidate);
        verifyNoMoreInteractions(repository);
    } 
    
    @Test 
    void findAll() {
        var candidates = Instancio.stream(Candidate.class).limit(10).toList();

        when(repository.findAll()).thenReturn(candidates);

        var result = service.findAll();

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
        assertEquals(result, candidates);
    }
}
