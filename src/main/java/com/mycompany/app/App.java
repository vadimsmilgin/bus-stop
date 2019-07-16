package com.mycompany.app;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class App
{
    public static void main(String[] args) {

        ArrayList<ModelEntry> input = new ArrayList<ModelEntry>();
        ArrayList<ModelEntry> output = new ArrayList<ModelEntry>();

        Scanner in = new Scanner(System.in);
        String path = in.nextLine();

        createInputList(path, input);
        output = createOutputList(input, output);

        writeInfo(output);
    }

    public static void createInputList(String path, List<ModelEntry> input){
        try(FileInputStream fstream = new FileInputStream(path)){
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;

            while ((strLine = br.readLine()) != null) {
                String[] arg = strLine.split(" ");
                input.add(new ModelEntry(arg[0], setTime(arg[1]), setTime(arg[2])));
            }
        } catch (IOException e){
            System.out.println("Error: File isn't found");
        }
    }

     public static Calendar setTime(String time){
         String[] args = time.split(":");
         Calendar date = new GregorianCalendar(2019,
                                               06,
                                               15,
                                                Integer.valueOf(args[0]),
                                                Integer.valueOf(args[1])
                                              );

         return date;
     }

    public static ArrayList<ModelEntry> createOutputList(ArrayList<ModelEntry> input, ArrayList<ModelEntry> output) {

        boolean checkShoterService = false;

        ArrayList<ModelEntry> temp = input.stream()
                                          .distinct()
                                          .filter(entry -> ((entry.getFinishTime().getTimeInMillis()
                                                  -entry.getStartTime().getTimeInMillis())/1000) <= 3600)
                                          .sorted(Comparator.comparing(ModelEntry::getStartTime))
                                          .collect(Collectors.toCollection(ArrayList::new));

        for(int i=0; i<temp.size(); i++){
            if((i+1)<temp.size()) {
                if(checkShoterService == true){
                    if ((temp.get(i).getFinishTime().getTimeInMillis() <= output.get(output.size()-1).getFinishTime().getTimeInMillis() &&
                            temp.get(i).getStartTime().getTimeInMillis() >= output.get(output.size()-1).getStartTime().getTimeInMillis()
                         ) |
                        (temp.get(i).getFinishTime().getTimeInMillis() >= output.get(output.size()-1).getFinishTime().getTimeInMillis() &&
                            temp.get(i).getStartTime().getTimeInMillis() <= output.get(output.size()-1).getStartTime().getTimeInMillis())) {

                        output.set(output.size()-1, temp.get(i));
                    } else {
                        checkShoterService = false;
                        i--;
                        continue;
                    }
                }
                else if (temp.get(i).getStartTime().equals(temp.get(i+1).getStartTime()) == true &&
                            temp.get(i).getFinishTime().equals(temp.get(i+1).getFinishTime()) == true) {

                    if (temp.get(i).getNameCompany().equals("Posh")) {
                        output.add(temp.get(i));
                        i++;
                    } else if (temp.get(i+1).getNameCompany().equals("Posh")) {
                        output.add(temp.get(i+1));
                        i++;
                    }
                }
                else if (temp.get(i).getStartTime().equals(temp.get(i+1).getStartTime()) == true) {

                    if(temp.get(i).getFinishTime().getTimeInMillis() < temp.get(i+1).getFinishTime().getTimeInMillis()) {
                        output.add(temp.get(i));
                        i++;
                        checkShoterService = true;
                    } else {
                        output.add(temp.get(i+1));
                        i++;
                        checkShoterService = true;
                    }
                }
                else if ((temp.get(i).getFinishTime().getTimeInMillis() <= temp.get(i+1).getFinishTime().getTimeInMillis()
                            && temp.get(i).getStartTime().getTimeInMillis() > temp.get(i+1).getStartTime().getTimeInMillis()
                          ) |
                          (temp.get(i).getFinishTime().getTimeInMillis() >= temp.get(i+1).getFinishTime().getTimeInMillis()
                            && temp.get(i).getStartTime().getTimeInMillis() < temp.get(i+1).getStartTime().getTimeInMillis())) {

                    if(temp.get(i).getStartTime().getTimeInMillis() > temp.get(i+1).getStartTime().getTimeInMillis()){
                       output.add(temp.get(i));
                        i++;
                        checkShoterService = true;
                   } else {
                       output.add(temp.get(i+1));
                       i++;
                       checkShoterService = true;
                   }
                }

                else {
                    output.add(temp.get(i));
                }
            } else output.add(temp.get(i));
        }

        Comparator<ModelEntry> comporator = Comparator.comparing(ModelEntry::getNameCompany).reversed();
        comporator = comporator.thenComparing(Comparator.comparing(entry -> entry.getStartTime()));

        output = output.stream().sorted(comporator).collect(Collectors.toCollection(ArrayList::new));

       return output;
    }

    public static void writeInfo(ArrayList<ModelEntry> output){

        boolean firstTimeGrotty = true;

        try(FileWriter outputFile = new FileWriter("C:\\Users\\Public\\output.txt")){
            for(ModelEntry i : output) {
                if (i.getNameCompany().equals("Posh") == true) {
                    outputFile.write(i.toString() + "\r\n");
                } else {
                    if(firstTimeGrotty == true){
                        outputFile.write("\r\n");
                        firstTimeGrotty = false;
                    }
                    outputFile.write(i.toString() + "\r\n");
                }
            }
        } catch (IOException e){
            System.out.println("Error: File wasn't created");
        }
    }
}


