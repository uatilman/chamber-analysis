package ru.tilman.chamberanalysis.controllers;

import ru.tilman.chamberanalysis.entity.Chamber;

import java.util.List;

public class ChamberAjax {

    private List<Chamber> chambers;

    public List<Chamber> getChambers() {
        return chambers;
    }

    public void setChambers(List<Chamber> chambers) {
        this.chambers = chambers;
    }
}
