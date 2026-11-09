---
cat_version: '0.3'
kind: tce
id: ndew.enderman.despawn.tce.rules
status: draft
process: ndew.enderman.despawn
source_refs:
  - https://drive.google.com/file/d/1CISIkE1HL_k_zpsgDf3KEEJfM-soT7wl/view
decision_ref: ''
refs:
  - ndew.enderman.despawn.pi.scan
semantic_contract: markdown-v2
language: ja
---

# TCE: ndew.enderman.despawn.tce.rules

網羅要求: partial

## 振る舞い
| ID | 起点 | 条件 | 結果 | 効果 |
| --- | --- | --- | ---: | --- |
| non-carrier | スキャン | `入力.ブロック保持 == 偽` | 1 | `出力.削除 = 偽; 状態.遠距離経過 = 0` |
| protected | スキャン | `入力.ブロック保持 == 真 かつ (入力.自然消滅許可 == 偽 または 入力.特別保護 == 真)` | 1 | `出力.削除 = 偽; 状態.遠距離経過 = 0` |
| peaceful | スキャン | `入力.ブロック保持 == 真 かつ 入力.自然消滅許可 == 真 かつ 入力.特別保護 == 偽 かつ 入力.ピースフル == 真` | 1 | `出力.削除 = 真` |

## 部分仕様の残項目

- 最短距離・閾値・遠距離tick累計・乱数確率集約はDrive仕様にあるが、本CAT Draftで機械式へ写像し終えていない。現時点でprocess-closureをPassedとしない。
