package pl.sdacademy.todolist.utils;

public class Consts {
    public final static String EMAIL_PATTERN = "^[^_][\\w]+[\\.\\w]*@[\\w]+(\\.[a-zA-Z]{2,})+$";
    public final static String CELLPHONE_NUMBER = "\\d{9}";
    public final static String PASSWORD_PATTERN = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{8,12}$";
}
