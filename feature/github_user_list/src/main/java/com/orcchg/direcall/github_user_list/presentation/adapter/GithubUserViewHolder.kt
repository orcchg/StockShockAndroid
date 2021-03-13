package com.orcchg.direcall.github_user_list.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.orcchg.direcall.github_user_list.databinding.RvGithubUserListItemBinding
import com.orcchg.direcall.github_user_list.domain.model.GithubUser

class GithubUserViewHolder(
    private val binding: RvGithubUserListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: GithubUser) {
        Glide.with(binding.ivAvatar)
            .load(model.avatarUrl)
            .into(binding.ivAvatar)

        binding.tvTitle.text = model.login
    }
}
