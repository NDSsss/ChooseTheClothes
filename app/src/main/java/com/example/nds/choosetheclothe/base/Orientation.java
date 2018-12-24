package com.example.nds.choosetheclothe.base;

public enum Orientation {

    HORIZONTAL {
        @Override
        Helper createHelper() {
            return new HorizontalHelper();
        }
    },
    VERTICAL {
        @Override
        Helper createHelper() {
            return new VerticalHelper();
        }
    };

    abstract Helper createHelper();



}
