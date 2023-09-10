package org.sbsplus.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.sbsplus.type.Category;
import org.sbsplus.user.dto.UserDto;
import org.sbsplus.user.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<User, UserDto> typeMap = modelMapper.createTypeMap(User.class, UserDto.class);
        
        typeMap.addMappings(mapper -> {
            mapper.using(ctx -> ((Category) ctx.getSource()).getValue())
                    .map(User::getCategory, UserDto::setCategory);
        });
        
        return modelMapper;
    }
}
