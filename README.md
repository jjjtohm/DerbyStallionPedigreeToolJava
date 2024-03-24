# DerbyStallionPedigreeToolJava

Java版Nintendo Switch版ダービースタリオンの配合分析ツール

# Requirement

java11<br>jackson

# How to build

必要なクラスパスを通したうえで、
javac src/PedTool.java

# Usage

実行すると標準出力に結果がcsv形式で出力される。

```bash
# 1代配合の例: ディープインパクト×ミココロノママニ
pedtool "ﾃﾞｨｰﾌﾟｲﾝﾊﾟｸﾄ" "ﾐｺｺﾛﾉﾏﾏﾆ"

# 2代配合の例: ディープインパクト×(ロードカナロア×ミココロノママニ)
pedtool "ﾃﾞｨｰﾌﾟｲﾝﾊﾟｸﾄ" "ﾛｰﾄﾞｶﾅﾛｱ" "ﾐｺｺﾛﾉﾏﾏﾆ"


# 種牡馬や繁殖牝馬の名前に"all"を指定することで全探索を行うことができる
# 大量に出力されるのでリダイレクトして表計算ソフトなどで開くこと推奨
pedtool "ﾃﾞｨｰﾌﾟｲﾝﾊﾟｸﾄ" "all" "all" >result.csv
```
