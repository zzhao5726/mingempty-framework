package top.mingempty.gateway.config;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import top.mingempty.commons.util.JsonUtil;
import top.mingempty.domain.base.MeRsp;
import top.mingempty.gateway.domain.GatewayProperties;

@Configuration
@AllArgsConstructor
public class MenuConfig {

    private final GatewayProperties gatewayProperties;


    @Bean
    public RouterFunction<?> routerFunction() {
        return RouterFunctions.route(
                RequestPredicates.POST("/api/auth-service/auth/menu/user"),
                this::gainUserMenu);
    }

    private Mono<ServerResponse> gainUserMenu(ServerRequest serverRequest) {
        String menuJson = "{\"mainMenus\":[{\"index\":\"metadata\",\"title\":\"公共数据管理\",\"icon\":\"Menu\"},{\"index\":\"1\",\"title\":\"主菜单-1\",\"icon\":\"Menu\"},{\"index\":\"2\",\"title\":\"主菜单-2\",\"icon\":\"Menu\"},{\"index\":\"3\",\"title\":\"主菜单-3\",\"icon\":\"Menu\"}],\"sideMenus\":[{\"mainIndex\":\"metadata\",\"children\":[{\"index\":\"metadata_entry\",\"title\":\"公共码值\",\"icon\":\"Menu\",\"url\":\"/entry\"}]},{\"mainIndex\":\"1\",\"children\":[{\"index\":\"1-1\",\"title\":\"二级菜单 1-1\",\"icon\":\"Menu\",\"url\":\"/1\"},{\"index\":\"1-2\",\"title\":\"二级菜单 1-2\",\"icon\":\"Menu\",\"children\":[{\"index\":\"1-2-1\",\"title\":\"三级菜单 1-2-1\",\"icon\":\"Menu\",\"children\":[{\"index\":\"1-2-1-1\",\"title\":\"四级菜单 1-2-1-1\",\"icon\":\"Menu\",\"url\":\"/2/1/1\"},{\"index\":\"1-2-1-2\",\"title\":\"四级菜单 1-2-1-2\",\"icon\":\"Menu\",\"url\":\"/2/1/2\"}]},{\"index\":\"1-2-2\",\"title\":\"三级菜单 1-2-2\",\"icon\":\"Menu\",\"url\":\"/2/2\"}]}]},{\"mainIndex\":\"2\",\"children\":[{\"index\":\"2-1\",\"title\":\"二级菜单-2-1\",\"icon\":\"Menu\",\"url\":\"/1\"},{\"index\":\"2-2\",\"title\":\"二级菜单-2-2\",\"icon\":\"Menu\",\"url\":\"/2\"},{\"index\":\"2-3\",\"title\":\"二级菜单-2-3\",\"icon\":\"Menu\",\"url\":\"/3\"}]},{\"mainIndex\":\"3\",\"children\":[{\"index\":\"3-1\",\"title\":\"二级菜单-3-1\",\"icon\":\"Menu\",\"url\":\"/1\"},{\"index\":\"3-2\",\"title\":\"二级菜单-3-2\",\"icon\":\"Menu\",\"url\":\"/2\"},{\"index\":\"3-3\",\"title\":\"二级菜单-3-3\",\"icon\":\"Menu\",\"children\":[{\"index\":\"3-3-1\",\"title\":\"三级菜单 3-3-1\",\"icon\":\"Menu\",\"children\":[{\"index\":\"3-3-1-1\",\"title\":\"四级菜单 3-3-1-1\",\"icon\":\"Menu\",\"url\":\"/3/1/1\"}]},{\"index\":\"3-3-2\",\"title\":\"三级菜单 3-3-2\",\"icon\":\"Menu\",\"children\":[{\"index\":\"3-3-2-1\",\"title\":\"四级菜单 3-3-2-1\",\"icon\":\"Menu\",\"url\":\"/3/2/1\"}]}]}]}]}";
        if (StrUtil.isEmpty(gatewayProperties.getDefaultMenu())) {
            return ServerResponse.ok().body(Mono.just(MeRsp.success(JsonUtil.toMap(menuJson))), MeRsp.class);
        }
        return ServerResponse.ok().body(Mono.just(MeRsp.success(JsonUtil.toMap(gatewayProperties.getDefaultMenu()))), MeRsp.class);
    }
}
