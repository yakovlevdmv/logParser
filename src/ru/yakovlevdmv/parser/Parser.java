package ru.yakovlevdmv.parser;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

            FileWriter fw = new FileWriter("result3.txt");
            for(Process p : result) {
//                System.out.println(p);
                fw.write(p.toString() + "\n");
            }


        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
