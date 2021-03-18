package net.den3.hackathonWeb.Store;

public interface IUserStatusStore {
    boolean isBusy(String userUUID);
    void setBusy(String userUUID);
}
