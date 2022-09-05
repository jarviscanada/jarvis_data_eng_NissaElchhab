package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

public class TweetControllerUnitTest {

  private Tweet sampleTweet;

  @Rule
  MockitoRule mockitoRule = MockitoJUnit.rule();

  @Mock
  Service mockService;

  @InjectMocks
  Controller mockController;

  @Before
  public void setUp() {

  }

  @Test
  public void postTweet() {
    String[] args = {"Hello Tweet", "10.10:14:14"};
    given(mockController.postTweet(args)).willReturn(sampleTweet);
    mockController.postTweet(args);

  }

  @Test
  public void showTweet() {
  }

  @Test
  public void deleteTweet() {
  }
}