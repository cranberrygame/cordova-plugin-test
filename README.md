Cordova Game plugin
====================

# Overview #
Show leaderboard and achievements (google play game and game center, SDK)
 
[android, ios] [cordova cli] [xdk]

Requires google play developer account https://play.google.com/apps/publish/<br>
Requires apple developer account https://developer.apple.com/devcenter/ios/index.action

This is open source cordova plugin.

You can see Plugins For Cordova in one page: http://cranberrygame.github.io?referrer=github

```c
cf)

Leaderboard game: Best score game
	Limited life
		ex) 1, 3
	Limited time
		ex) 30 seconds
	Time is score
	
Achievement
	Score
		ex)	Achievement1 (Score 10)
			Achievement2 (Score 30)
			Achievement3 (Score 60)
			Achievement4 (Score 100)
			Achievement5 (Score 150)
	Level
		ex)	Achievement1 (Level 1)
			Achievement2 (Level 2)
			Achievement3 (Level 4)
			Achievement4 (Level 6)
			Achievement5 (Level 8)
			Achievement6 (Level 10)
	Category
		ex)	Achievement1 (Number)
			Achievement1 (Fruit)
			Achievement1 (Color)
			Achievement1 (Other)
			Achievement1 (Challenge (Limited time))
```
# Change log #
```c
```
# Install plugin #

## Cordova cli ##
https://cordova.apache.org/docs/en/edge/guide_cli_index.md.html#The%20Command-Line%20Interface - npm install -g cordova@5.0.0
```c
//caution: replace YOUR_GOOGLE_PLAY_GAME_APP_ID ex) 1064334934918
cordova plugin add cordova-plugin-game --variable APP_ID="YOUR_GOOGLE_PLAY_GAME_APP_ID"
```
## Xdk ##
//caution: replace YOUR_GOOGLE_PLAY_GAME_APP_ID ex) 1064334934918<br>
<img src="https://raw.githubusercontent.com/cranberrygame/cordova-plugin-game/master/doc/xdk_APP_ID.png"><br>
https://github.com/cranberrygame/cordova-plugin-game/blob/master/doc/intelxdk.config.additions.xml
```c
```

## Cocoon ##
https://cocoon.io - Create project - [specific project] - Setting - Plugins - Custom - Git Url: https://github.com/cranberrygame/cordova-plugin-game.git - INSTALL - Save<br>
//caution: replace YOUR_GOOGLE_PLAY_GAME_APP_ID ex) 1064334934918<br>
https://cocoon.io - Create project - [specific project] - Setting - Plugins - Installed - Git Url https://github.com/cranberrygame/cordova-plugin-game.git - ADD PARAMETER - Name: APP_ID Value: YOUR_GOOGLE_PLAY_GAME_APP_ID - Save<br>
<img src="https://raw.githubusercontent.com/cranberrygame/cordova-plugin-game/master/doc/cocoon_APP_ID"><br>

## Phonegap build service (config.xml) ##
https://build.phonegap.com/ - Apps - [specific project] - Update code - Zip file including config.xml
```c
//caution: replace YOUR_GOOGLE_PLAY_GAME_APP_ID ex) 1064334934918<br>
<gap:plugin name="cordova-plugin-game" source="npm" >
    <param name="APP_ID" value="YOUR_GOOGLE_PLAY_GAME_APP_ID" />
</gap:plugin>
```

## Construct2 ##
Download construct2 plugin: https://dl.dropboxusercontent.com/u/186681453/pluginsforcordova/game/construct2.html
<br>
Now all the native plugins are installed automatically: https://plus.google.com/102658703990850475314/posts/XS5jjEApJYV
# Server setting #

<img src="https://raw.githubusercontent.com/cranberrygame/cordova-plugin-game/master/doc/1_YOUR_GOOGLE_PLAY_GAME_APP_ID.png"><br>
<img src="https://raw.githubusercontent.com/cranberrygame/cordova-plugin-game/master/doc/2_YOUR_GOOGLE_PLAY_GAME_APP_ID.png"><br>
<img src="https://raw.githubusercontent.com/cranberrygame/cordova-plugin-game/master/doc/3.png"><br>
<img src="https://raw.githubusercontent.com/cranberrygame/cordova-plugin-game/master/doc/4.png"><br>
<img src="https://raw.githubusercontent.com/cranberrygame/cordova-plugin-game/master/doc/5.png"><br>
<img src="https://raw.githubusercontent.com/cranberrygame/cordova-plugin-game/master/doc/6_if_Signing_certificate_fingerprint_(SHA1)_is_blank.png"><br>
<img src="https://raw.githubusercontent.com/cranberrygame/cordova-plugin-game/master/doc/7.png"><br>
<img src="https://raw.githubusercontent.com/cranberrygame/cordova-plugin-game/master/doc/8.png"><br>

```c
[android]

//add game
google play developer console - Game services - Add a new game - Enter the name of your game: Test App, Category: Puzzle

//get YOUR_GOOGLE_PLAY_GAME_APP_ID
google play developer console - Game services - [specific app] - get YOUR_GOOGLE_PLAY_GAME_APP_ID (the number that appears beside the game name in the header of the Developer Console, e.g. "Test App - 12345678",. The YOUR_GOOGLE_PLAY_GAME_APP_ID in this case is 12345678.)

//link app
google play developer console - Game services - [specific app] - Linked App - Android - Save and continue - Authorize your app now - Continue - Create Client (if Signing certificate fingerprint (SHA1) is blank, do it after publishing app in alpha, beta, or normal (after publishing, it will be filled automatically))

//add leaderboard, get YOUR_GOOGLE_PLAY_GAME_LEADERBOARD_ID
google play developer console - Game services - [specific app] - leaderboard - Add leaderboard - Name: Leaderboard - get YOUR_GOOGLE_PLAY_GAME_LEADERBOARD_ID
 
//add achievements (must minimum 5 achievements), get YOUR_GOOGLE_PLAY_GAME_ACHIEVEMENT_ID1, YOUR_GOOGLE_PLAY_GAME_ACHIEVEMENT_ID2, YOUR_GOOGLE_PLAY_GAME_ACHIEVEMENT_ID3, YOUR_GOOGLE_PLAY_GAME_ACHIEVEMENT_ID4, YOUR_GOOGLE_PLAY_GAME_ACHIEVEMENT_ID5
google play developer console - Game services - [specific app] - achievement - Add achievement - Name: Achievement1 (Score 10), Description: Achievement1 (Score 10) - get YOUR_GOOGLE_PLAY_GAME_ACHIEVEMENT_ID1
google play developer console - Game services - [specific app] - achievement - Add achievement - Name: Achievement2 (Score 30), Description: Achievement2 (Score 30) - get YOUR_GOOGLE_PLAY_GAME_ACHIEVEMENT_ID2
google play developer console - Game services - [specific app] - achievement - Add achievement - Name: Achievement3 (Score 60), Description: Achievement3 (Score 60) - get YOUR_GOOGLE_PLAY_GAME_ACHIEVEMENT_ID3
google play developer console - Game services - [specific app] - achievement - Add achievement - Name: Achievement4 (Score 100), Description: Achievement4 (Score 100) - get YOUR_GOOGLE_PLAY_GAME_ACHIEVEMENT_ID4
google play developer console - Game services - [specific app] - achievement - Add achievement - Name: Achievement5 (Score 150), Description: Achievement5 (Score 150) - get YOUR_GOOGLE_PLAY_GAME_ACHIEVEMENT_ID5

//publish game
google play developer console - Game services - [specific app] - prepare test - publish game

[ios]

itunesconnect - 나의 App - [specific app] - Game Center - Enable for Single Game

//leaderboard
itunesconnect - 나의 App - [specific app] - Game Center - Add Leaderboard -
단일 순위표 - 
순위표 세트 식별 정보: testapp_leaderboard - 
순위표 ID: testapp_leaderboard -
점수 형식 유형: Integer -
점수 제출 유형: 가장 높은 점수
정렬 순서: 높은 점수순 -
Add Language -
언어: English -
이름: Leaderboard -
점수 형식: Integer (100,000,122) -
Save

//achievement1
itunesconnect - 나의 App - [specific app] - Game Center - 목표 달성 추가 -
목표 달성 식별 정보: testapp_achievement1
목표 달성 ID: testapp_achievement1
점수 값: 25 (max 100)
숨김: No
여러 번 달성 가능: No
언어 추가 - 
언어: English 
제목: Achievement1 (Score 10)
사전 획득한 목표 달성 설명: Achievement1 (Score 10)
획득한 목표 달성 설명: Achievement1 (Score 10)
이미지: 512x512 png

//achievement2
itunesconnect - 나의 App - [specific app] - Game Center - 목표 달성 추가 -
목표 달성 식별 정보: testapp_achievement2
목표 달성 ID: testapp_achievement2
점수 값: 50 (max 100)
숨김: No
여러 번 달성 가능: No
언어 추가 - 
언어: English 
제목: Achievement2 (Score 30)
사전 획득한 목표 달성 설명: Achievement2 (Score 30)
획득한 목표 달성 설명: Achievement2 (Score 30)
이미지: 512x512 png

//achievement3
itunesconnect - 나의 App - [specific app] - Game Center - 목표 달성 추가 -
목표 달성 식별 정보: testapp_achievement3
목표 달성 ID: testapp_achievement3
점수 값: 75 (max 100)
숨김: No
여러 번 달성 가능: No
언어 추가 - 
언어: English 
제목: Achievement3 (Score 60)
사전 획득한 목표 달성 설명: Achievement3 (Score 60)
획득한 목표 달성 설명: Achievement3 (Score 60)
이미지: 512x512 png

//achievement4
itunesconnect - 나의 App - [specific app] - Game Center - 목표 달성 추가 -
목표 달성 식별 정보: testapp_achievement4
목표 달성 ID: testapp_achievement4
점수 값: 100 (max 100)
숨김: No
여러 번 달성 가능: No
언어 추가 - 
언어: English 
제목: Achievement4 (Score 100)
사전 획득한 목표 달성 설명: Achievement4 (Score 100)
획득한 목표 달성 설명: Achievement4 (Score 100)
이미지: 512x512 png

//achievement5
itunesconnect - 나의 App - [specific app] - Game Center - 목표 달성 추가 -
목표 달성 식별 정보: testapp_achievement5
목표 달성 ID: testapp_achievement5
점수 값: (leave blank, max 100)
숨김: No
여러 번 달성 가능: No
언어 추가 - 
언어: English 
제목: Achievement5 (Score 150)
사전 획득한 목표 달성 설명: Achievement5 (Score 150)
획득한 목표 달성 설명: Achievement5 (Score 150)
이미지: 512x512 png

can test before publish
```
# API #
```javascript
//
var leaderboardId = "REPLACE_THIS_WITH_YOUR_LEADERBOARD_ID";
var achievementId1 = "REPLACE_THIS_WITH_YOUR_ACHIEVEMENT_ID1";
var achievementId2 = "REPLACE_THIS_WITH_YOUR_ACHIEVEMENT_ID2";
var achievementId3 = "REPLACE_THIS_WITH_YOUR_ACHIEVEMENT_ID3";
var achievementId4 = "REPLACE_THIS_WITH_YOUR_ACHIEVEMENT_ID4";

//
document.addEventListener("deviceready", function(){
	window.game.setUp();

	//callback
	window.game.onLoginSucceeded = function(result) {
		var playerDetail = result;
        alert('onLoginSucceeded: ' + playerDetail['playerId'] + ' ' + playerDetail['playerDisplayName']);
    };	
    window.game.onLoginFailed = function() {
        alert('onLoginFailed');
    };
    window.game.onGetPlayerImageSucceeded = function(result) {
		var playerImageUrl = result;
        alert('onGetPlayerImageSucceeded: ' + playerImageUrl);
    };
    window.game.onGetPlayerImageFailed = function() {
        alert('onGetPlayerImageFailed');
    };	
    window.game.onGetPlayerScoreSucceeded = function(result) {
		var playerScore = result;
        alert('onGetPlayerScoreSucceeded: ' + playerScore);
    };
    window.game.onGetPlayerScoreFailed = function() {
        alert('onGetPlayerScoreFailed');
    };
	//	
    window.game.onSubmitScoreSucceeded = function() {
        alert('onSubmitScoreSucceeded');
    };	
    window.game.onSubmitScoreFailed = function() {
        alert('onSubmitScoreFailed');
    };	
	//	
    window.game.onUnlockAchievementSucceeded = function() {
        alert('onUnlockAchievementSucceeded');
    };  
    window.game.onUnlockAchievementFailed = function() {
        alert('onUnlockAchievementFailed');
    };
    window.game.onIncrementAchievementSucceeded = function() {
        alert('onIncrementAchievementSucceeded');
    };  
    window.game.onIncrementAchievementFailed = function() {
        alert('onIncrementAchievementFailed');
    };
    window.game.onResetAchievementsSucceeded = function() {
        alert('onResetAchievementsSucceeded');
    };	
    window.game.onResetAchievementsFailed = function() {
        alert('onResetAchievementsFailed');
    };
}, false);

//
window.game.login();
window.game.logout();
alert(window.game.isLoggedIn());
window.game.getPlayerImage();
window.game.getPlayerScore(leaderboardId);

//
window.game.submitScore(leaderboardId, 5);//leaderboardId, score
window.game.showLeaderboard(leaderboardId);

//
window.game.unlockAchievement(achievementId1);
window.game.unlockAchievement(achievementId2);
window.game.unlockAchievement(achievementId3);
window.game.unlockAchievement(achievementId4);
window.game.incrementAchievement(achievementId1, 2); //achievementId, incrementalStepOrCurrentPercent: Incremental step (android) or current percent (ios) for incremental achievement.
window.game.incrementAchievement(achievementId2, 2);
window.game.incrementAchievement(achievementId3, 2);
window.game.incrementAchievement(achievementId4, 2);
window.game.showAchievements();
window.game.resetAchievements();//only supported on ios
```
# Examples #
<a href="https://github.com/cranberrygame/com.cranberrygame.phonegap.plugin.game/blob/master/example/index.html">example/index.html</a>

# Test #

```c
[android] [xdk]

//publish as alpha test (recommend) or beta test instead of production.
google play developer console - [specific app] - APK - Alpha test - Upload as alpha test - Drag and drop apk and publish now as alpha test.

//add test user for game
google play developer console - Game services - [specific game] - test - add tester (google play account)

//add test community for alpha test or beta test download
google play developer console - 
All applications - 
[specific app] - 
APK -
Alpha testers - 
Manage list of testers - 
Add Google groups or Google+ community: https://plus.google.com/communities/xxx (if you want make Google+ Community, go to this: https://plus.google.com/communities) -
Add - 
Let test user download and install apk from this url: https://play.google.com/apps/testing/YOUR_PACKAGE (invite test user in your Google+ community, wait until this url is available, take hours)

[ios]

iphone - Setting - Game Center - activate sand box mode - login with sand box account in the app
```

# Useful links #

Plugins For Cordova<br>
http://cranberrygame.github.io?referrer=github

# Credits #
