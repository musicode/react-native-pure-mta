
#import "MTA.h"
#import "MTAConfig.h"
#import "MTA+Account.h"

#import "RNTMTA.h"

@implementation RNTMTA

RCT_EXPORT_MODULE(RNTMTA)

RCT_EXPORT_METHOD(start:(NSString *)appKey isDebug:(BOOL)isDebug) {
    
    if (isDebug) {
        [[MTAConfig getInstance] setDebugEnable:YES];
    }
    
    [MTA startWithAppkey:appKey];
}

RCT_EXPORT_METHOD(addPhoneAccount:(NSString *)phone) {
    
    MTAAccountInfo *info = [MTAAccountInfo new];
    
    info.type = MTAAccountPhone;
    info.account = phone;
    info.accountStatus = MTAAccountStatusNormal;
    
    [MTA reportAccountExt:@[info]];
    
}

RCT_EXPORT_METHOD(removePhoneAccount:(NSString *)phone) {
    
    MTAAccountInfo *info = [MTAAccountInfo new];
    
    info.type = MTAAccountPhone;
    info.account = phone;
    info.accountStatus = MTAAccountStatusLogout;
    
    [MTA reportAccountExt:@[info]];
    
}

RCT_EXPORT_METHOD(addWechatAccount:(NSString *)openId) {
    
    MTAAccountInfo *info = [MTAAccountInfo new];
    
    info.type = MTAAccountWeixin;
    info.account = openId;
    info.accountStatus = MTAAccountStatusNormal;
    
    [MTA reportAccountExt:@[info]];
    
}

RCT_EXPORT_METHOD(removeWechatAccount:(NSString *)openId) {
    
    MTAAccountInfo *info = [MTAAccountInfo new];
    
    info.type = MTAAccountWeixin;
    info.account = openId;
    info.accountStatus = MTAAccountStatusLogout;
    
    [MTA reportAccountExt:@[info]];
    
}

RCT_EXPORT_METHOD(trackCustomEvent:(NSString *)name options:(NSDictionary*)options) {
    
    [MTA trackCustomKeyValueEvent:name
                            props:options];
    
}

@end
