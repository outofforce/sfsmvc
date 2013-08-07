package com.springapp.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
@RequestMapping("/")
public class HelloController {
    @Autowired
    public JdbcTemplate db;

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
        String sql ="select * from project";
        db.execute(sql);

        model.addAttribute("message", "Hello world!");

		return "hello";
	}
}