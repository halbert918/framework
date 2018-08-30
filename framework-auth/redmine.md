com.framework.auth
        security: 服务端token校验
        client: 客户端发送请求前，先获取认证服务的token,根据将获取的token放入请求request的header中
                启动需使用RestTemplate的扩展类AuthRestTemplate,如：
                @Bean
                AuthRestTemplate authRestTemplate() {
                    AuthRestTemplate restTemplate = new AuthRestTemplate();
                    restTemplate.setMessageConverters(fastJsonHttpMessageConverters.getConverters());
                    return restTemplate;
                }
        
