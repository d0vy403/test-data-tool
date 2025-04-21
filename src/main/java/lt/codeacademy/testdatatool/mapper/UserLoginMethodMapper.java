package lt.codeacademy.testdatatool.mapper;

import lt.codeacademy.testdatatool.dto.CreateUserLoginMethodRequest;
import lt.codeacademy.testdatatool.dto.GetUserLoginMethodResponse;
import lt.codeacademy.testdatatool.entity.UserLoginMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserIdToUserDataConverter.class})
public interface UserLoginMethodMapper {

    @Mapping(source = "method", target = "method")
    @Mapping(source = "userId", target = "userData", qualifiedByName = "convertToUserData")
    UserLoginMethod toUserLoginMethod(CreateUserLoginMethodRequest createUserLoginMethodRequest);

    @Mapping(source = "method", target = "method")
    @Mapping(source = "userData.id", target = "userId")
    GetUserLoginMethodResponse toGetUserLoginMethodResponse(UserLoginMethod userLoginMethod);

}
