
#import "MTA.h"
#import "MTAConfig.h"

#import "RNTMTA.h"

@implementation RNTMTA

RCT_EXPORT_MODULE(RNTMTA)

RCT_EXPORT_METHOD(start:(NSString *)appKey isDebug:(BOOL)isDebug) {
    
    if (isDebug) {
        [[MTAConfig getInstance] setDebugEnable:YES];
    }
    
    [MTA startWithAppkey:appKey];
}

@end
