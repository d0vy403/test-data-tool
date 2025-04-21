package lt.codeacademy.testdatatool.mapper;

import lt.codeacademy.testdatatool.dto.*;
import lt.codeacademy.testdatatool.entity.UserData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    uses = {AccountDataMapper.class, UserLoginMethodMapper.class})
public interface UserDataMapper {

  @Mapping(source = "userName", target = "username")
  @Mapping(source = "password", target = "password")
  @Mapping(source = "channel", target = "channel")
  @Mapping(source = "environment", target = "environment")
  UserData toUserData(CreateUserDataRequest request);

  @Mapping(source = "username", target = "userName")
  @Mapping(source = "account", target = "account")
  @Mapping(source = "userLoginMethod", target = "userLoginMethod")
  GetUserDataResponse toGetUserDataResponse(UserData userData);

  @Mapping(source = "userName", target = "username")
  void updateUserData(UpdateUserDataRequest request, @MappingTarget UserData userData);
}
