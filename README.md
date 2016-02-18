# HeadDictionary v1.0.0-SNAPSHOT

建築などで使えるプレイヤーヘッドを簡単に扱うためのBukkitプラグインです。

## 動作環境

* Java 8以降
* [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/)
* Spigot 1.8.8(動作確認はしていませんが、Bukkit 1.8以降で動きと思います)

## コマンド

コマンド | 説明
------- | ----
`/heads` | 全てのヘッドを表示します
`/heads <辞書名>` | 辞書名に対応したヘッドを表示します
`/heads search <キーワード>` | キーワードを元に検索します<br>正規表現を利用出来ます

## デフォルト辞書

デフォルトの辞書データは[Quality Heads](http://heads.freshcoal.com/maincollection.php)を元に作成しました

辞書名 | 説明
------ | ---
`Alphabet` | 英数字や記号
`Blocks` | Minecraftバニラのブロック風
`Characters` | キャラクター
`Color` | 単色
`Devices` | 機械類
`Food` | 食べ物
`Games` | ゲーム
`Interior` | 装飾
`Mobs` | Minecraftバニラのモブ
`Pokemon` | ポケモン(?)
`Services` | インターネットサービス(SNS等)のアイコン

## ヘッドの追加方法

インストールした後生成される`plugins/HeadDictionary/heads`フォルダの中のファイルを参考にして追加

## Special Thanks

* [FreshCoal](http://heads.freshcoal.com/maincollection.php) - デフォルトの辞書データ
* [NekonekoNetwork](https://www.nekonekoserver.net/) - ~~Jenkinsの提供~~
