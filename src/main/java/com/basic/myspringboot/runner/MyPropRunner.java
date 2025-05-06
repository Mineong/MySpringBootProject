package com.basic.myspringboot.runner;

import com.basic.myspringboot.property.MyPropProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyPropRunner implements CommandLineRunner {

    private final MyPropProperties props;

    public MyPropRunner(MyPropProperties props) {
        this.props = props;
    }

    @Override
    public void run(String... args) {
        System.out.println("사용자 이름: " + props.getFullName());
        System.out.println("사용자 나이: " + props.getAge());
    }
}

