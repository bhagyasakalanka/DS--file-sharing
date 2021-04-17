
//@RestController
//public class APIHandler {
//
//    @Autowired
//    private NodeOpsWS nodeOpsWS;
//
//    @RequestMapping(value = "/search", method = RequestMethod.POST)
//    @ResponseBody
//    public String root(@RequestBody String json) {
//        SearchRequest searchRequest = new Gson().fromJson(json, SearchRequest.class);
//        nodeOpsWS.triggerSearchRequest(searchRequest);
//        return "SUCCESS";
//    }
//}
