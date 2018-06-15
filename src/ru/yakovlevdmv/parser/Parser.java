package ru.yakovlevdmv.parser;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {

    public static void parse(String filePath) {
        ArrayList<Process> processes = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {

            stream.forEach(line -> {
                String[] tmp = line.split(" ");
                int id = Integer.parseInt(tmp[5].split(":")[1].replace(")", ""));
                LocalDateTime time = LocalDateTime.parse(line.split(" ")[0].replace( "," , "." ));
                String name = tmp[5].split(":")[0].replace("(", "");
                String type = tmp[3];

                if (type.equals("exit")) {
                    Optional<Process> matchingObject = processes.stream().
                            filter(p -> p.getID() == id && p.getName().equals(name)).
                            findFirst();



                    if (matchingObject.isPresent()) {
                        Process process = matchingObject.get();
                        process.setTimeOut(time);
                    }

                } else {

                    Process p = new Process();
                    p.setID(id);
                    p.setName(name);
                    p.setTimeIn(time);

                    processes.add(p);
                }
            });

            List<Process> result = processes.stream().filter(process -> process.getTimeOut() != null).collect(Collectors.toList());

            FileWriter fw = new FileWriter("result5.txt");
            for(Process p : result) {
                fw.write(p.toString() + "\n");
            }
            fw.close();

            result.stream()
                    .filter(distinctByKey(Process::getName))
                    .map(process -> process.getName())
                    .forEach(s -> {
                        long count = result.stream().filter(process -> process.getName().equals(s)).count();
                        double average = result.stream().filter(process -> process.getName().equals(s)).mapToInt(Process::getTime).average().getAsDouble();
                        long min = result.stream().filter(process -> process.getName().equals(s)).mapToInt(Process::getTime).min().getAsInt();
                        long max = result.stream().filter(process -> process.getName().equals(s)).mapToInt(Process::getTime).max().getAsInt();
                        int maxID = result.stream().filter(process -> process.getName().equals(s)).filter(process -> process.getTime() == max).mapToInt(Process::getID).findFirst().getAsInt();


//                        System.out.println("sum: " + sum);
//                        System.out.println("\tmin: " + min);
//                        System.out.println("\tmax: " + max);
//                        System.out.println("\tavg: " + average);
//                        System.out.println("\tcount: " + count);
//                        System.out.println("\tmax id: " + maxID);
                        System.out.println("OperationsImpl:" + s + " min " + min + ",max " + max + ",avg " + average + ",max id" + maxID + ",count " + count);
                    });

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /*
    Source: http://www.lambdafaq.org/how-can-i-get-distinct-to-compare-some-derived-value-instead-of-the-stream-elements-themselves/
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T,Object> keyExtractor) {
        Map<Object,String> seen = new ConcurrentHashMap<>();
        return t -> seen.put(keyExtractor.apply(t), "") == null;
    }


}
