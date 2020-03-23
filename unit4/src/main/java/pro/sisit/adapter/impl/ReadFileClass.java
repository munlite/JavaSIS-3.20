package pro.sisit.adapter.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ReadFileClass {

    //Лист строк из файла
    private List<String> listOfLinesFromAFile;

    /**
     * Метод для чтения файла
     * @return Лист строк из файла
     */
    public List<String> readFile(Path path){
        listOfLinesFromAFile = new ArrayList<>();
        try (LineIterator lineIteratorFile = FileUtils.lineIterator(path.toFile())){
            {
                while (lineIteratorFile.hasNext()){
                    // записываем все строки в List
                    listOfLinesFromAFile.add(lineIteratorFile.nextLine());
                }
                return listOfLinesFromAFile;
            }
        }catch (IOException e){ throw new RuntimeException("Файл не прочитан"); }

    }

}
