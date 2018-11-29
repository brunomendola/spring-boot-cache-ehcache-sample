package net.brunomendola.demo.springbootcache;

import io.micrometer.core.instrument.Statistic;
import net.brunomendola.demo.springbootcache.service.TaskService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootCacheApplication.class)
public class SpringBootCacheApplicationTests {

  @Autowired
  private CacheManager cacheManager;
  @Autowired
  private MetricsEndpoint metricsEndpoint;
  @Autowired
  private TaskService taskService;

  @Before
  public void setUp() {
    Optional.ofNullable(cacheManager.getCache("tasks"))
        .ifPresent(Cache::clear);
  }

  @Test
  public void givenNoQuery_CountNoGets() {
    double getsCountBefore = getGetsCount();
    double hitCountBefore = getHitCount();
    double missCountBefore = getMissCount();

    Assert.assertEquals(getsCountBefore, getGetsCount(), 0);
    Assert.assertEquals(hitCountBefore, getHitCount(), 0);
    Assert.assertEquals(missCountBefore, getMissCount(), 0);
  }

  @Test
  public void givenOneQuery_CountOneGetAndOneMiss() {
    double getsCountBefore = getGetsCount();
    double hitCountBefore = getHitCount();
    double missCountBefore = getMissCount();

    taskService.getByAssigneeName("Bruno");

    Assert.assertEquals(getsCountBefore + 1, getGetsCount(), 0);
    Assert.assertEquals(hitCountBefore, getHitCount(), 0);
    Assert.assertEquals(missCountBefore + 1, getMissCount(), 0);
  }

  @Test
  public void givenTwoQueries_CountTwoGetsAndOneMissAndOneHit() {
    double getsCountBefore = getGetsCount();
    double hitCountBefore = getHitCount();
    double missCountBefore = getMissCount();

    taskService.getByAssigneeName("Bruno");
    taskService.getByAssigneeName("Bruno");

    Assert.assertEquals(getsCountBefore + 2, getGetsCount(), 0);
    Assert.assertEquals(missCountBefore + 1, getMissCount(), 0);
    Assert.assertEquals(hitCountBefore + 1, getHitCount(), 0);
  }

  @Test
  public void givenThreeQueries_CountThreeGetsAndOneMissAndTwoHits() {
    double getsCountBefore = getGetsCount();
    double hitCountBefore = getHitCount();
    double missCountBefore = getMissCount();

    taskService.getByAssigneeName("Bruno");
    taskService.getByAssigneeName("Bruno");
    taskService.getByAssigneeName("Bruno");

    Assert.assertEquals(getsCountBefore + 3, getGetsCount(), 0);
    Assert.assertEquals(missCountBefore + 1, getMissCount(), 0);
    Assert.assertEquals(hitCountBefore + 2, getHitCount(), 0);
  }

  private double getGetsCount() {
    return getCacheGetsMetric().map(this::getCountMeasurement).orElse(0.0);
  }

  private double getHitCount() {
    return getCacheGetsMetric("result:hit").map(this::getCountMeasurement).orElse(0.0);
  }

  private double getMissCount() {
    return getCacheGetsMetric("result:miss").map(this::getCountMeasurement).orElse(0.0);
  }

  private Optional<MetricsEndpoint.MetricResponse> getCacheGetsMetric(String... tags) {
    return Optional.ofNullable(metricsEndpoint.metric("cache.gets", Arrays.asList(tags)));
  }

  private Double getCountMeasurement(MetricsEndpoint.MetricResponse metric) {
    return metric.getMeasurements().stream().filter(s -> s.getStatistic().equals(Statistic.COUNT)).findAny().orElseThrow(IllegalArgumentException::new).getValue();
  }
}
