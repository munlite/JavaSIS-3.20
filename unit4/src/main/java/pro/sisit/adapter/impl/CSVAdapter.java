package pro.sisit.adapter.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import pro.sisit.adapter.IOAdapter;
import pro.sisit.model.Author;
import pro.sisit.model.Book;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.FileUtils.*;

// 1. TODO: написать реализацию адаптера

public class CSVAdapter<T> implements IOAdapter<T> {
    private Class<T> entityType;
    private Path pathToFIle;
    private List<String> listOfLinesFromFile;

    public CSVAdapter(Class<T> entityType, Path path) {

        this.entityType = entityType;
        this.pathToFIle = path;
    }

    @Override
    public T read(int index) {
        listOfLinesFromFile = new ArrayList<>();
        if (entityType.getName().equals("pro.sisit.model.Author")) {
            try (LineIterator lineIteratorFile = FileUtils.lineIterator(pathToFIle.toFile())) { //создаем итератор для файла
                while (lineIteratorFile.hasNext()){
                    listOfLinesFromFile.add(lineIteratorFile.nextLine()); // записываем все строки в List
                }
                if (convertListToObject(index, listOfLinesFromFile) != null) // проверяем полученный объект
                    return convertListToObject(index, listOfLinesFromFile); // метод, который создает нужный объект с параметрами из листа
                else throw new NullPointerException();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if (entityType.getName().equals("pro.sisit.model.Book")){
            try (LineIterator lineIteratorFile = lineIterator(pathToFIle.toFile());) {
                while (lineIteratorFile.hasNext()){
                    listOfLinesFromFile.add(lineIteratorFile.nextLine());
                }
                if (convertListToObject(index, listOfLinesFromFile) != null)
                    return convertListToObject(index, listOfLinesFromFile);
                else throw new NullPointerException();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public int append(T entity) {
        int indexLineInFile =1; // индекс последней строки при нахождении объекта

        if (entity instanceof Author) {
            boolean checkingSuccessfulSearch = false; //флаг, используемый для определения нахождения нужного поля
            try (LineIterator lineIteratorFile = lineIterator(pathToFIle.toFile());) {
                while (lineIteratorFile.hasNext()) {
                    if (((Author) entity).getName().equals(lineIteratorFile.nextLine())) {
                        checkingSuccessfulSearch = true;
                        indexLineInFile++;
                        break;
                    } else indexLineInFile++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return checkingSuccessfullSearchInFile(checkingSuccessfulSearch, indexLineInFile,2); //метод для перевода индекса последней строи в индек объекта
        } else if (entity instanceof Book) {
            boolean checkingSuccessfulSearch = false;
            try (LineIterator lineIteratorFile = lineIterator(pathToFIle.toFile());) {
                while (lineIteratorFile.hasNext()) {
                    if (((Book) entity).getIsbn().equals(lineIteratorFile.nextLine())) {
                        checkingSuccessfulSearch = true;

                        break;
                    } else indexLineInFile++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return checkingSuccessfullSearchInFile(checkingSuccessfulSearch, indexLineInFile,4);
        }
        return indexLineInFile;
    }
    private int checkingSuccessfullSearchInFile(boolean checkingSuccessfulSearch, int indexLineInFile, int numberOfLines){
        if (checkingSuccessfulSearch)
            return indexLineInFile/numberOfLines;
        else return -1;
    }
    private T convertListToObject(int rowIndex, List<String> list){
        if (entityType.getName().equals("pro.sisit.model.Author")){
            try {
                return (T) entityType.getDeclaredConstructor(String.class, String.class).newInstance(list.get(rowIndex*2-2), list.get(rowIndex*2-1));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        else if (entityType.getName().equals("pro.sisit.model.Book")){
            try {
                return (T) entityType.getDeclaredConstructor(String.class, String.class,String.class, String.class).newInstance(
                        list.get(rowIndex*4-4), list.get(rowIndex*4-3),
                        list.get(rowIndex*4-2), list.get(rowIndex*4-1));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }


        }else return null;
        return null;
    }

}
