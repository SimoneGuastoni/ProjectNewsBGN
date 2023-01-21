package com.example.projectnewsbgn.Models;

public abstract class Result {
    private Result() {}

    public boolean isSuccess() {
        if (this instanceof Success) {
            return true;
        } else {
            return false;
        }
    }

    public static final class Success extends Result {
        private final NewsResponse newsResponse;
        public Success(NewsResponse newsResponse) {
            this.newsResponse = newsResponse;
        }
        public NewsResponse getData() {
            return newsResponse;
        }
    }

    public static final class Error extends Result{
        private final String message;
        public Error(String message) { this.message = message;}
        public String getMessage() { return message;}
    }
}
