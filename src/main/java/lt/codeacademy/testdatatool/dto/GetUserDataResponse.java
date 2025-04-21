package lt.codeacademy.testdatatool.dto;

import java.util.ArrayList;
import java.util.List;
import lt.codeacademy.testdatatool.entity.Channel;
import lt.codeacademy.testdatatool.entity.Environment;

public record GetUserDataResponse(
    Long id,
    String userName,
    String password,
    Environment environment,
    Channel channel,
    List<GetAccountDataResponse> account,
    List<GetUserLoginMethodResponse> userLoginMethod)
{
  public GetUserDataResponse {
    if (account == null) {
      account = new ArrayList<>();
    }
    if (userLoginMethod == null) {
      userLoginMethod = new ArrayList<>();
    }
  }
}
