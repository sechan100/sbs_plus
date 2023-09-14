package org.sbsplus.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.sbsplus.general.type.Category;
import org.sbsplus.domain.user.dto.UserDto;
import org.sbsplus.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ModelMapperConfig {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Bean
    public ModelMapper userToDto(){
        
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<User, UserDto> typeMap = modelMapper.createTypeMap(User.class, UserDto.class);
        
        typeMap.addMappings(mapper -> {
            mapper.using(ctx -> ((Category) ctx.getSource()).getValue()).map(User::getCategory, UserDto::setCategory);
        });
        
        return modelMapper;
    }
    
    @Bean
    public ModelMapper dtoToUser(){
        
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<UserDto, User> typeMap = modelMapper.createTypeMap(UserDto.class, User.class);
        
        typeMap.addMappings(mapper -> {
            // String -> Enum
            mapper.using(ctx -> (Category.convertStringToEnum((String)ctx.getSource()))).map(UserDto::getCategory, User::setCategory);
            
            // rawPassword -> encodedPassword
            mapper.using(ctx -> passwordEncoder.encode((String)ctx.getSource())).map(UserDto::getPassword, User::setPassword);
        });
        
        return modelMapper;
    }
}