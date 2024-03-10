package com.example.demo.kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class KafkaMessage<T> {

  /**
   * The data contained in the Kafka message.
   */
  private T data;

  /**
   * Constructs a new Kafka message with the provided data.
   *
   * @param data The data to be encapsulated in the Kafka message.
   */
  public KafkaMessage(final T data) {
    this.data = data;
  }

  /**
   * Gets the data from the Kafka message.
   *
   * @return The data contained in the Kafka message.
   */
  public T getData() {
    return data;
  }

  /**
   * Sets the data for the Kafka message.
   *
   * @param data The data to be set in the Kafka message.
   */
  public void setData(T data) {
    this.data = data;
  }

  /**
   * Converts the data inside the KafkaMessage to the specified target class.
   *
   * @param targetClass The class to which the data should be converted.
   * @return An instance of the target class with the converted data.
   */
  public <S> S convertTo(Class<S> targetClass) {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(this.data, targetClass);
  }

  /**
   * Converts the data inside the KafkaMessage to a list of the specified target class.
   *
   * @param <T>          The type parameter for the target class.
   * @param targetClass  The class to which the data items should be converted.
   * @return A list of instances of the target class with the converted data.
   */
  public <T> List<T> convertListTo(Class<T> targetClass) {
    ModelMapper modelMapper = new ModelMapper();

    Type listType = new TypeToken<List<Map<String, Object>>>() {}.getType();
    List<Map<String, Object>> dataList = modelMapper.map(this.data, listType);

    return dataList.stream()
            .map(dataItem -> modelMapper.map(dataItem, targetClass))
            .collect(Collectors.toList());
  }

}