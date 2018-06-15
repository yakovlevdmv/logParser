package ru.yakovlevdmv.parser;

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

        ArrayList<Method> methods = new ArrayList<>();

        //try to get stream object from file
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            //read file line by line
            stream.forEach(line -> {
                String[] tmp = line.split(" ");
                int id = Integer.parseInt(tmp[5].split(":")[1].replace(")", ""));
                LocalDateTime time = LocalDateTime.parse(line.split(" ")[0].replace( "," , "." ));
                String name = tmp[5].split(":")[0].replace("(", "");
                String type = tmp[3];

                // add timeout if method was completed
                if (type.equals("exit")) {
                    //searching for exited process by name:id
                    Optional<Method> matchingObject = methods.stream().
                            filter(p -> p.getID() == id && p.getName().equals(name)).
                            findFirst();

                    if (matchingObject.isPresent()) {
                        Method method = matchingObject.get();
                        method.setTimeOut(time);
                    }

                } else {
                    //Create new Method
                    Method p = new Method();

                    p.setID(id);
                    p.setName(name);
                    p.setTimeIn(time);

                    //Push method to methods list
                    methods.add(p);
                }
            });

            //Ignoring methods which was not completed
            List<Method> result = methods.stream().filter(method -> method.getTimeOut() != null).collect(Collectors.toList());

            result.stream()
                    // Getting method names and ...
                    .filter(distinctByKey(Method::getName))
                    .map(method -> method.getName())
                    // ... for each method calculate necessary data
                    .forEach(s -> {
                        long count = result.stream().filter(method -> method.getName().equals(s)).count();
                        double average = result.stream().filter(method -> method.getName().equals(s)).mapToInt(Method::getTime).average().getAsDouble();
                        long min = result.stream().filter(method -> method.getName().equals(s)).mapToInt(Method::getTime).min().getAsInt();
                        long max = result.stream().filter(method -> method.getName().equals(s)).mapToInt(Method::getTime).max().getAsInt();
                        int maxID = result.stream().filter(method -> method.getName().equals(s)).filter(method -> method.getTime() == max).mapToInt(Method::getID).findFirst().getAsInt();


//                        System.out.println("sum: " + sum);
//                        System.out.println("\tmin: " + min);
//                        System.out.println("\tmax: " + max);
//                        System.out.println("\tavg: " + average);
//                        System.out.println("\tcount: " + count);
//                        System.out.println("\tmax id: " + maxID);
                        System.out.println("OperationsImpl:" + s + " min " + min + ",max " + max + ",avg " + average + ",max id " + maxID + ",count " + count);
                    });

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /*
        An predicate to get distinct properties from Object array
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T,Object> keyExtractor) {
        Map<Object,String> seen = new ConcurrentHashMap<>();
        return t -> seen.put(keyExtractor.apply(t), "") == null;
    }


}
