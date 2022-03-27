package com.michaelloi.user.constants;

public class QueryValues {
    public static class registerQuery {
        public static final String insertUser =
            "INSERT INTO public.tbl_user(" +
                "userid, " +
                "username, " +
                "usernickname, " +
                "userfullname, " +
                "userdateofbirth, " +
                "lastlogin, " +
                "sessionid, " +
                "isactive, )" +
                "password)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        public static final String getRunningId =
            "SELECT COUNT(userid) " +
                "FROM public.tbl_user";

        public static final String insertAccount =
            "DO $$ " +
                "DECLARE " +
                "acctccy varchar;" +
                "BEGIN " +
                "FOR acctccy IN select currencycode from public.tbl_currency " +
                "LOOP " +
                "INSERT INTO public.tbl_account(" +
                "accountid," +
                "accountno," +
                "accountccy," +
                "accountbalance," +
                "userid)" +
                "VALUES (" +
                "uuid_generate_v4()," +
                "?," +
                "acctccy," +
                "0," +
                "?);" +
                "END LOOP;" +
                "END$$;";
    }

    public static class loginQuery {
        public static final String loginUser =
            "SELECT " +
                "userid, " +
                "username, " +
                "usernickname, " +
                "userfullname," +
                "userdateofbirth," +
                "lastlogin," +
                "sessionid," +
                "isactive," +
                "password" +
                "FROM public.tbl_user" +
                "WHERE username = ? " +
                "and password = ? ;";

        public static final String getAccount =
            "SELECT " +
                "accountid " +
                "accountno " +
                "accountccy " +
                "accountbalance " +
                "userid " +
                "FROM public.tbl_account " +
                "WHERE userid = ? LIMIT 1;";
    }

}
