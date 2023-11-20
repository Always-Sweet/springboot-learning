# Kafka Guide

一个由 Linkedin 公司开发的分布式、支持分区的（partition）、多副本的（replica）、基于 zookeeper 协调的消息系统，以其高吞吐量低延迟、持久性、容错性等特点，广泛应用于大数据领域。

**基础概念**

1）生产者与消费者

对于 kafka 来说客户端分为：生产者 Producer 和 消费者 Consumer，根据其通讯模式生产者发送消息到队列，消费从队列中拉取消息处理。

2）主题（Topic）和分区（Partition）

消息以主题来分类，每一个主题对应一个消息队列，根据业务需要拆分不同的消息到不同的队列，专事专办。把主题比作一项运输任务，那分区就是运输的路线，同一个消息队列可以通过不同的分区承担消费和存储任务，不同的分区之间独立，在生产数据时可以根据一定规则将数据写入 Topic 下的指定分区中，以实现进一步的消息分类。

以下是两者数量不相等时暴露的问题：

- 若 consumer 数量大于 partition 数量，会造成限制的consumer，产生浪费
- 若 consumer 数量小于 partition 数量，会导致均衡失效，其中的某个或某些 consumer 会消费更多的任务

🐞*有序消费问题*

> 分区内消息是有序的，但不同分区的消息是无序的

方法一：可以选择只通过一个 partition 依照分区内有序的特性保证有序消费

方法二：在多分区时，需要有序消费的消息可以发送到指定 partition 消费

3）消费组（Consumer Group）

多个消费者组成一个消费组。在 kafka 的设计中，一个分区只能被同一消费组内的某一消费者消费，同一消费组的消费者可以消费同一 topic 下的不同分区，从而提高吞吐量！

| ![](D:\workspace\practice-master\springboot-master\code\springboot-kafka\Snipaste_2023-10-11_11-20-14.png) | ![](D:\workspace\practice-master\springboot-master\code\springboot-kafka\Snipaste_2023-10-11_11-20-20.png) |
| ------------------------------------------------------------ | ------------------------------------------------------------ |

4）Broker 和集群（Cluster）

一个 kafka 服务器成为一个 Broker，它接受生产者发送的消息并存入磁盘；Broker 同时服务消费者拉取分区消息的请求，返回目前已经提交的消息。若干个 Broker 组成一个集群，其中集群内某个 Broker 会成为集群控制器（Cluster Controller），它负责管理集群，包括分配分区到 Broker、监控 Broker 故障等。

> kafka 使用分区将 topic 分散在多个分区并将它们存储在不同的 broker 上，多个分区按照分区消费一对一的最佳消费方案，从消费维度上提高了并行消费能力，集群下分区在不同的 broker 可以让消费者请求不同的机器拉取消息，分散了机器层面的性能压力，进一步提高了吞吐量！