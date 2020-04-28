package pro.sisit.adapter.impl;

import pro.sisit.adapter.AdaptedForCSVobject;
import pro.sisit.adapter.IOAdapter;
import pro.sisit.model.Author;
import pro.sisit.model.Book;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CSVAdapter<T extends AdaptedForCSVobject> implements IOAdapter<T> {
    private Class<T> entityType;
    private Path pathToFIle;
    private List<String> listOfLinesFromFile;

    public  CSVAdapter(Class<T> entityType, Path path) {

        this.entityType = entityType;
        this.pathToFIle = path;
    }

    @Override
    public T read(int index) {
        T objectClassAdapter = null;
        ReadFileClass readFileClass = new ReadFileClass();
        listOfLinesFromFile = new ArrayList<>();
        try {
            objectClassAdapter = entityType.getDeclaredConstructor().newInstance();
            listOfLinesFromFile = readFileClass.readFile(pathToFIle);
            objectClassAdapter = convertListToObject(objectClassAdapter, listOfLinesFromFile, index);
            if (objectClassAdapter != null){
                return objectClassAdapter;

            } else { throw new RuntimeException("Пустой объект"); }

        } catch (Exception e) { throw new RuntimeException("Ошибка поиска объекта по индесу"); }
    }

    /**
     * Метод поиска объекта в файле
     * @param entity
     * @return
     */
    @Override
    public int append(T entity) {
        //Класс для чтения файла и записи всех строк в лист
        ReadFileClass readFileClass = new ReadFileClass();
        if (entity instanceof Author) {
            return searchAuthor(readFileClass.readFile(pathToFIle), ((Author) entity).getName());
        } else if (entity instanceof Book) {
            return searchBook(readFileClass.readFile(pathToFIle), ((Book) entity).getIsbn());
        } else {throw new RuntimeException("Файла с таким типом объекта не существует"); }

    }

    /**
     * Метод преобразования индекса из файла в индекс объекта
     * @param indexLineInFile индекс из файла
     * @param numberOfLines число строк выделенных для записи одного объекта
     * @return индекс объекта
     */
    private int checkingSuccessfullSearchInFile(int indexLineInFile, int numberOfLines){
        return indexLineInFile/numberOfLines;
    }

    /**
     * Метод для конвертирования строк из файла в объект.
     * @param t Объект
     * @param list лист со строками из файла
     * @param index индекс объекта в файле
     * @return заполненый объект
     */
    private T convertListToObject(T t, List<String> list, int index) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        if (!list.isEmpty()){
            if (t instanceof Book) {
                 t.fillingInAnObjectField(list.subList(index*4-4, index*4));
                 return t;
            }else if (t instanceof Author){
                t.fillingInAnObjectField(list.subList(index*2-2, index*2));
                return t;

            }else { throw new RuntimeException("Такого обьекта не существует"); }
        }else { throw new RuntimeException("Лист строк пуст"); }
    }

    /**
     * Метод поиска книги в файле
     * @param lists лист строк из файла
     * @param isbn уникальный индетефикатор
     * @return индекс объекта в файле
     */
    private int searchBook(List<String> lists, String isbn) {

        if (!lists.isEmpty()) {
            if(lists.indexOf(isbn)!=-1)
                return checkingSuccessfullSearchInFile(lists.indexOf(isbn)+1,4);
            else { throw new RuntimeException("Файл с книгами не содержит этот объект"); }
        } else {
            throw new RuntimeException(("Лист строк пуст"));
        }

    }
    /**
     * Метод поиска автора в файле
     * @param lists лист строк из файла
     * @param authorsName фамилия автора
     * @return индекс объекта в файле
     */
    private int searchAuthor(List<String> lists, String authorsName) {
        if (!lists.isEmpty()) {
            if(lists.indexOf(authorsName)!=-1)
                return checkingSuccessfullSearchInFile(lists.indexOf(authorsName)+2,2);
            else { throw new RuntimeException("Файл с авторами не содержит этот объект"); }
        } else {
            throw new RuntimeException(("Лист строк пуст"));
        }
    }
}
