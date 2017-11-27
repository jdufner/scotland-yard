package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.position.Startposition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class StartpositionServiceTest {

  @InjectMocks
  private StartpositionService underTest;

  @Test
  public void testZieheFreieStartposition_whenZweiGezogen_expectZweiUnterschiedliche() {
    // arrange

    // act
    Startposition startposition1 = underTest.zieheFreieStartposition();
    Startposition startposition2 = underTest.zieheFreieStartposition();

    // assert
    assertThat(startposition1).isNotEqualTo(startposition2);
  }

}
