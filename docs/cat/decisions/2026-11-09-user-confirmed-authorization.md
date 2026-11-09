# User normative decision — NormalDespawnEndermanWithBlocks

- Date: 2026-11-09 (Asia/Tokyo)
- Evidence class: **explicit user instruction in this ChatGPT conversation**, transcribed here for durable provenance.
- Approver: repository/project user (the author of the cited user turns), **not the assistant**.
- Recorded by: ChatGPT implementation assistant.
- Approval scope: semantics of Process `ndew.enderman.despawn`, PI `ndew.enderman.despawn.pi.scan`, TCE `ndew.enderman.despawn.tce.rules`, describing finite decision branches of normal despawning for block-carrying Endermen.

## User's actual directives

1. 「機能を分けるのではなく、そのプラグインを現在のサーバーに合わせて改修する目的です。機能としては、バニラではブロックを持ったエンダーマンがデスポーンしないせいで負荷が上がるため、ブロックを持っていてもデスポーンするようにします。」
2. 「では、設計と実装をお願いします。リポジトリはNormalDespawnEndermanWithBlocksという名前で作成しておきました。」
3. 「では実機確認だけになるまでやってください。」
4. 「じゃあConfirmed承認作業をしてください。要件は既に伝えてますよね。」

## Interpretation of the current authorized scope

The user has explicitly instructed approval work based on the already-stated feature requirement. The approved core contract is **allowing block-carrying Endermen to despawn under ordinary hostile-mob rules while retaining normal protections**. The current eight-branch Process/PI/TCE Draft refines this requirement using default Paper despawn cases, player eligibility and deletion/reset outcomes.

This decision record **does not claim the user separately dictated** soft=32, hard=128, minimum=600, chance=1/800 or scan=20. These remain documented, configurable implementation defaults derived from the chosen vanilla-approximation approach. It also does not assert bit-for-bit vanilla/NMS behavior, server runtime test success, or complete CAT implementation conformance. The first three Semantic Artifacts are approved as a *finite decision-boundary specification*; numeric and integration details require separate verification against the Drive design and runtime.

This record is a truthful transcript of the user's instructions; it is **not** a fabricated ChatGPT reviewer approval, not a substitute for canonical compiler Gates and not an independent provider test. The explicit instruction in item 4 is the normative approval authorization for the scoped promotion.

## Source of current design

- Google Drive `forGPT/XPlayServer/OriginalPlugins/NormalDespawnEndermanWithBlocks/仕様.md`: https://drive.google.com/file/d/1CISIkE1HL_k_zpsgDf3KEEJfM-soT7wl/view
- Before-promotion Draft Git commit: `104b82f76f119af0316603d28c73d578fbff5dfa`
- CAT runtime authority executor: `surumeneco/CAT-TDD` `develop` `896cae8313a533a3c74073a8c3150daad314ff90`, `.cat-system/scripts/cat_promote.py`.

## Lifecycle

Promotion is limited to changing `status` and `decision_ref` and moving the **unchanged** Process/PI/TCE Semantic Artifact bodies to `docs/cat/confirmed/`. Any subsequent semantic change requires fresh normative authorization.
