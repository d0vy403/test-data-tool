package lt.codeacademy.testdatatool.mapper;

import lt.codeacademy.testdatatool.dto.CreateAccountDataRequest;
import lt.codeacademy.testdatatool.dto.GetAccountDataResponse;
import lt.codeacademy.testdatatool.dto.UpdateAccountDataRequest;
import lt.codeacademy.testdatatool.entity.AccountData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    uses = {UserIdToUserDataConverter.class})
public interface AccountDataMapper {

  @Mapping(source = "accountNumber", target = "accountNumber")
  @Mapping(source = "type", target = "type")
  @Mapping(source = "balance", target = "balance")
  @Mapping(source = "currency", target = "currency")
  @Mapping(source = "blocked", target = "blocked")
  @Mapping(source = "userId", target = "userData", qualifiedByName = "convertToUserData")
  AccountData toAccountData(CreateAccountDataRequest accountData);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "accountNumber", target = "accountNumber")
  @Mapping(source = "type", target = "type")
  @Mapping(source = "balance", target = "balance")
  @Mapping(source = "currency", target = "currency")
  @Mapping(source = "blocked", target = "blocked")
  @Mapping(source = "userData.id", target = "userId")
  GetAccountDataResponse toGetAccountDataResponse(AccountData accountData);

  @Mapping(source = "type", target = "type")
  @Mapping(source = "currency", target = "currency")
  @Mapping(source = "blocked", target = "blocked")
  void updateAccountData(UpdateAccountDataRequest request, @MappingTarget AccountData accountData);
}
