package com.example.emergencyguide.Evacuation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emergencyguide.R
import com.example.emergencyguide.databinding.FragmentEvacuateTipsBinding

class EvacuateTipsFragment(private val idx: Int) : Fragment() {
    lateinit var binding: FragmentEvacuateTipsBinding
    private lateinit var evacuateTipsAdapter: EvacuateTipsAdapter
    private var evacuateTipsData: ArrayList<EvacuateTipsData> = arrayListOf()
    private var disasterType = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEvacuateTipsBinding.inflate(layoutInflater)
        disasterType = activity?.intent?.getStringExtra("disaster").toString()

        setDemmy()
        setRecyclerView()

        return binding.root
    }

    private fun setRecyclerView() {
        evacuateTipsAdapter = EvacuateTipsAdapter(requireContext(), evacuateTipsData)
        binding.rvEvaTips.adapter = evacuateTipsAdapter
        binding.rvEvaTips.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setDemmy() {
        if (disasterType == "지진") {
            if (idx == 0) {
                evacuateTipsData = arrayListOf(
                    EvacuateTipsData("1. 집 안에서의 안전을 확보합니다.", "∙ 탁자 아래와 같이 집 안에서 대피할 수 있는 안전한 대피 공간을 미리 파악해 둡니다.\n∙ 유리창이나 넘어지기 쉬운 가구 주변 등 위험한 위치를 확인해 두고 지진 발생 시 가까이 가지 않도록 합니다."),
                    EvacuateTipsData("2. 집 안에서 떨어지기 쉬운 물건을 고정합니다.", "∙ 가구나 가전제품이 흔들릴 때 넘어지지 않도록 고정해 둡니다.\n∙ 텔레비전, 꽃병 등 떨어질 수 있는 물건은 높은 곳에 두지 않도록 합니다."),
                    EvacuateTipsData("3. 집을 안전하게 관리합니다.", "∙ 가스 및 전기를 미리 점검합니다."),
                    EvacuateTipsData("4. 평상시 가족회의를 통하여 위급한 상황에 대비합니다.","∙ 머물고 있는 곳 주위의 넓은 공간 등 대피할 수 있는 장소를 알아 둡니다.\n∙ 비상시 가족과 만날 곳과 연락할 방법을 정해 둡니다."),
                    EvacuateTipsData("5. 평소 비상용품을 잘 준비해 둡니다.", "∙ 지진에 대비하여 비상용품을 준비해 두고, 보관 장소와 사용방법을 알아 둡니다."),
                )
            } else {
                evacuateTipsData = arrayListOf(
                    EvacuateTipsData("1. 지진으로 흔들릴 때는?","∙ 지진으로 흔들리는 동안은 탁자 아래로 들어가 몸을 보호하고, 탁자 다리를 꼭 잡습니다."),
                    EvacuateTipsData("2. 흔들림이 멈췄을 때는?","∙ 흔들림이 멈추면 전기와 가스를 차단하고,문을 열어 출구를 확보합니다."),
                    EvacuateTipsData("3. 건물 밖으로 나왔을 때는?", "∙ 건물 밖에서는 가방이나 손으로 머리를 보호하며, 건물과 거리를 두고 주위를 살피며 대피합니다."),
                    EvacuateTipsData("4. 집안에 있을 경우", "∙ 탁자 아래로 들어가 몸을 보호합니다.\n∙ 흔들림이 멈추면 전기와 가스를 차단하고 문을 열어 출구를 확보한 후, 밖으로 나갑니다."),
                    EvacuateTipsData("5.집밖에 있을 경우", "∙ 떨어지는 물건에 대비하여 가방이나 손으로 머리를 보호하며, 건물과 거리를 두고 운동장이나 공원 등 넓은 공간으로 대피합니다."),
                    EvacuateTipsData("6. 엘리베이터에 있을 경우", "∙ 모든 층의 버튼을 눌러 가장 먼저 열리는 층에서 내린 후 계단을 이용합니다.\n※ 지진 시 엘리베이터를 타면 안됩니다."),
                    EvacuateTipsData("7. 전철을 타고 있을 경우", "∙ 손잡이나 기둥을 잡아 넘어지지 않도록 합니다.\n∙ 전철이 멈추면 안내에 따라 행동합니다.")
                )
            }
        } else {
            if (idx == 0) {
                evacuateTipsData = arrayListOf(
                    EvacuateTipsData("1. 주변 사람에게 알립니다.","∙ 자고 있을 때 화재경보가 울리면 불이 났는지 확인하려고 하기보다는 소리를 질러 주변 사람에게 알립니다."),
                    EvacuateTipsData("2. 대피방법을 결정합니다.", "∙ 손등으로 출입문 손잡이를 만져보아 손잡이가 따뜻하거나 뜨거우면 문 반대쪽에 불이 난 것이므로 문을 열지 않습니다.\n∙ 연기 들어오는 방향과 출입문 손잡이를 만져보아 계단으로 나갈지 창문으로 구조를 요청할지 결정합니다"),
                    EvacuateTipsData("3. 신속히 대피합니다.","∙ 대피할 때는 엘리베이터를 절대 이용하지 않고 계단을 통하여 지상으로 안전하게 대피합니다.\n∙ 대피가 어려운 경우에는 창문으로 구조요청을 하거나 대피공간 또는 경량칸막이를 이용하여 대피합니다."),
                    EvacuateTipsData("4. 119로 신고합니다.", "∙ 안전하게 대피한 후 119에 신고합니다."),
                )
            } else {
                evacuateTipsData = arrayListOf(
                    EvacuateTipsData("1. 연기가 발생하거나 불이 난 것을 보았을 때","∙ 불이 난 것을 발견하면 “불이야!”라고 소리치거나 비상벨을 눌러 주변에 알리도록 합니다."),
                    EvacuateTipsData("2. 불을 끌 것인지 대피할 것인지 판단합니다.","∙ 불길이 천장까지 닿지 않은 작은 불이라면 소화기나 물양동이 등을 활용하여 신속히 끄도록 합니다.\n∙ 불길이 커져서 대피해야 할 경우 젖은 수건 또는 담요를 활용하여 계단을 통해 밖으로 대피합니다.\n∙ 세대 밖으로 대피가 어려운 경우 경량칸막이를 이용하여 이웃집으로 대피하거나 완강기를 이용하여 창문으로 나가는 방법, 실내대피 공간으로 대피하였다가 불이 꺼진 후 나오는 방법 등을 활용합니다.")
                )
            }
        }
    }
}