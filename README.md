# Head Dictionary v1.0.0-SNAPSHOT

Head Dictionaryは、簡単に使えるモブヘッドを出すためのBukkit Pluginです

* [Jenkins](http://jenkins.nekonekoserver.net/job/HeadDictionary/)(最新開発版はここからダウンロード可能)  
  ※このJenkinsの管理は、プラグイン作者ではなくNekoneko Networkによって行われています。

## 注意事項

* Java 8の新機能を利用しているため、Java 7以前では動きません
* [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/)を導入する必要があります
* Spigot最新版にて動作確認をしています

## インストール

1. CraftBukkit/Spigotサーバーを用意する
2. [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/)を導入する
3. このプラグインを導入する
4. `/reload`又は再起動する

## コマンド

コマンド | 説明
------- | ----
`/heads` | 全てのヘッドを表示します
`/heads <辞書名>` | 辞書名に対応したヘッドを表示します
`/heads search <キーワード>` | キーワードを元に検索します<br>正規表現を利用出来ます

## デフォルト辞書

デフォルトの辞書データは[Quality Heads](http://heads.freshcoal.com/maincollection.php)を元に作成しました

辞書名一覧

* `food`
* `devices`
* `misc`
* `alphabet`
* `interior`
* `color`
* `blocks`
* `mobs`
* `games`
* `characters`
* `pokemon`
* `services`
* `xmas`

## ヘッドの追加

いつか書く

## Special Thanks

* [FreshCoal](http://heads.freshcoal.com/maincollection.php) - デフォルトの辞書データ
* [NekonekoNetwork](https://www.nekonekoserver.net/) - Jenkinsの提供
