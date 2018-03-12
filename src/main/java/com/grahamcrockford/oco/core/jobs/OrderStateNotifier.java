package com.grahamcrockford.oco.core.jobs;

import javax.annotation.Nullable;

import org.mongojack.Id;
import org.mongojack.ObjectId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;
import com.grahamcrockford.oco.core.spi.Job;
import com.grahamcrockford.oco.core.spi.JobBuilder;

@AutoValue
@JsonDeserialize(builder = OrderStateNotifier.Builder.class)
public abstract class OrderStateNotifier implements Job {

  public static final Builder builder() {
    return new AutoValue_OrderStateNotifier.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(withPrefix = "")
  public static abstract class Builder implements JobBuilder<OrderStateNotifier> {
    @JsonCreator private static Builder create() { return OrderStateNotifier.builder(); }
    @Override
    @Id @ObjectId
    public abstract Builder id(String value);
    public abstract Builder exchange(String exchange);
    public abstract Builder description(String description);
    public abstract Builder orderId(String orderId);
    @Override
    public abstract OrderStateNotifier build();
  }

  @Override
  @JsonIgnore
  public abstract Builder toBuilder();

  @Override
  @JsonProperty
  @Nullable
  @Id @ObjectId
  public abstract String id();

  @JsonProperty
  public abstract String exchange();

  @JsonIgnore
  @Override
  public final Class<OrderStateNotifierProcessor.Factory> processorFactory() {
    return OrderStateNotifierProcessor.Factory.class;
  }

  @JsonProperty
  public abstract String description();

  @JsonProperty
  public abstract String orderId();

}