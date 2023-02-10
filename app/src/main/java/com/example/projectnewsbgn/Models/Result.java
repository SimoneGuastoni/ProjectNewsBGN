package com.example.projectnewsbgn.Models;

public abstract class Result {
    private Result() {}

    public boolean isSuccess() {
        return this instanceof NewsSuccess || this instanceof AccountSuccess;
    }

    public static final class NewsSuccess extends Result {
        private final NewsResponse newsResponse;
        public NewsSuccess(NewsResponse newsResponse) {
            this.newsResponse = newsResponse;
        }
        public NewsResponse getData() {
            return newsResponse;
        }
    }

    public static final class AccountSuccess extends Result {
        private final Account account;
        public AccountSuccess(Account account) {
            this.account = account;
        }
        public Account getData() {
            return account;
        }
    }

    public static final class Error extends Result{
        private final String message;
        public Error(String message) { this.message = message;}
        public String getMessage() { return message;}
    }
}
